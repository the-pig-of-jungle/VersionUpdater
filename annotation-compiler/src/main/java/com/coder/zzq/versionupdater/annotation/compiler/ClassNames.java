package com.coder.zzq.versionupdater.annotation.compiler;

import com.squareup.javapoet.ClassName;

public class ClassNames {
    public static final ClassName BASE_AUTO_DETECT_OBSERVER =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "AbstractAutoDetectObserver");

    public static final ClassName BASE_MANUAL_DETECT_OBSERVER =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "AbstractManualDetectObserver");

    public static final ClassName DETECT_OBSERVER_REGISTER = ClassName.get("com.coder.zzq.versionupdaterlib",
            "DetectObserverRegister");
    public static final ClassName CHECK_CONFIG = ClassName.get("com.coder.zzq.versionupdaterlib",
            "CheckConfig");

    public static final ClassName DOWNLOAD_EVENT_LIVE_DATA =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "DownloadEventLiveData");

    public static final ClassName DOWNLOAD_EVENT_VIEW_MODEL =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "DownloadEventViewModel");

    public static final ClassName VIEW_MODEL_PROVIDER =
            ClassName.get("androidx.lifecycle",
                    "ViewModelProvider");
    public static final ClassName DETECT_OBSERVER_REGISTER_INTERFACE =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "IDetectObserverRegister");

    public static final ClassName APPLICATION =
            ClassName.get("android.app", "Application");

    public static final ClassName VERSION_UPDATER_INITIALIZER =
            ClassName.get("com.coder.zzq.versionupdaterlib",
                    "AppUpdaterInitializer");

    public static final ClassName TOOLKIT =
            ClassName.get("com.coder.zzq.toolkit", "Toolkit");

    public static final ClassName DETECT_OBSERVER_REGISTER_PROVIDER =
            ClassName.get("com.coder.zzq.versionupdaterlib.communication",
                    "DetectObserverRegisterProvider");
}
