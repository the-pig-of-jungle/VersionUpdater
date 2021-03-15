package com.coder.zzq.versionupdater.annotation.compiler;

import com.coder.zzq.versionupdater.annotations.AutoCheck;
import com.coder.zzq.versionupdater.annotations.ManualCheck;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class VersionUpdaterProcessor extends AbstractProcessor {
    private final String[] supportedAnnotations = {
            AutoCheck.class.getCanonicalName(),
            ManualCheck.class.getCanonicalName(),
    };

    private ProcessingEnvironment mProcessingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mProcessingEnvironment = processingEnvironment;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList(supportedAnnotations));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        TypeSpec.Builder detectObserverRegisterClassBuilder = TypeSpec.classBuilder(ClassNames.DETECT_OBSERVER_REGISTER)
                .addSuperinterface(ClassNames.DETECT_OBSERVER_REGISTER_INTERFACE);
        TypeElement autoDetectObserver = null;
        TypeElement manualDetectObserver = null;
        for (TypeElement element : set) {
            if (element.getQualifiedName().toString().equals(AutoCheck.class.getCanonicalName())) {
                autoDetectObserver = processAutoCheckAnnotation(roundEnvironment);
            } else if (element.getQualifiedName().toString().equals(ManualCheck.class.getCanonicalName())) {
                manualDetectObserver = processManualCheckAnnotation(roundEnvironment);
            }
        }

        MethodSpec.Builder registerMethodBuilder = MethodSpec.methodBuilder("register")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(ClassNames.CHECK_CONFIG, "checkConfig").build())
                .addStatement("$T downloadEventLiveData =\n" +
                                "                new $T(checkConfig.getObserverPage().getViewModelStoreOwner())\n" +
                                "                        .get($T.class)\n" +
                                "                        .downloadEvent()",
                        ClassNames.DOWNLOAD_EVENT_LIVE_DATA,
                        ClassNames.VIEW_MODEL_PROVIDER,
                        ClassNames.DOWNLOAD_EVENT_VIEW_MODEL
                );
        registerMethodBuilder.beginControlFlow("if(!downloadEventLiveData.hasObservers())");
        if (autoDetectObserver != null) {
            registerMethodBuilder.addStatement("downloadEventLiveData.observe(checkConfig.getObserverPage().getLifecycleOwner(), new $T(checkConfig.getObserverPage().getActivityContext()))",
                    ClassName.get(autoDetectObserver));
        }

        if (manualDetectObserver != null) {
            registerMethodBuilder.beginControlFlow("if(checkConfig.getDetectMode() == CheckConfig.DETECT_MODE_MANUAL)")
                    .addStatement("downloadEventLiveData.observe(checkConfig.getObserverPage().getLifecycleOwner(), new $T(checkConfig.getObserverPage().getActivityContext()))",
                            ClassName.get(manualDetectObserver))
                    .endControlFlow();
        }

        registerMethodBuilder.endControlFlow();

        registerMethodBuilder.addStatement("checkConfig.getObserverPage().release()")
                .build();
        TypeSpec detectObserverRegisterClass = detectObserverRegisterClassBuilder
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(registerMethodBuilder.build())
                .build();


        try {
            JavaFile.builder("com.coder.zzq.version_updater", detectObserverRegisterClass)
                    .build()
                    .writeTo(mProcessingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TypeSpec initializer = TypeSpec.classBuilder(ClassNames.VERSION_UPDATER_INITIALIZER)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(
                        MethodSpec.methodBuilder("initialize")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .returns(TypeName.VOID)
                                .addParameter(
                                        ParameterSpec.builder(ClassNames.APPLICATION, "application")
                                                .build()
                                )
                                .addStatement("$T.init(application)", ClassNames.TOOLKIT)
                                .addStatement("$T.setDetectObserverRegister(new $T())", ClassNames.DETECT_OBSERVER_REGISTER_PROVIDER, ClassNames.DETECT_OBSERVER_REGISTER)
                                .build()
                )
                .build();

        try {
            JavaFile.builder("com.coder.zzq.version_updater", initializer)
                    .build()
                    .writeTo(mProcessingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    private TypeElement processManualCheckAnnotation(RoundEnvironment roundEnvironment) {
        List<Element> elements = new ArrayList<>(roundEnvironment.getElementsAnnotatedWith(ManualCheck.class));
        if (elements.size() > 1) {
            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "multiple classes annotated with ManualCheck, only one do");
            return null;
        }

        TypeElement typeElement = (TypeElement) elements.get(0);

        if (!ClassName.get(typeElement.getSuperclass()).equals(ClassNames.BASE_MANUAL_DETECT_OBSERVER)) {
            mProcessingEnvironment.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "auto detect observer:" + typeElement.getSimpleName() + " must extends " + ClassNames.BASE_MANUAL_DETECT_OBSERVER.simpleName()
            );
            return null;
        }


        return typeElement;
    }

    private TypeElement processAutoCheckAnnotation(RoundEnvironment roundEnvironment) {
        List<Element> elements = new ArrayList<>(roundEnvironment.getElementsAnnotatedWith(AutoCheck.class));
        if (elements.size() > 1) {
            mProcessingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "multiple classes annotated with AutoCheck, only one do");
            return null;
        }

        TypeElement typeElement = (TypeElement) elements.get(0);

        if (!ClassName.get(typeElement.getSuperclass()).equals(ClassNames.BASE_AUTO_DETECT_OBSERVER)) {
            mProcessingEnvironment.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "auto detect observer:" + typeElement.getSimpleName() + " must extends " + ClassNames.BASE_AUTO_DETECT_OBSERVER.simpleName()
            );
            return null;
        }


        return typeElement;

    }
}
