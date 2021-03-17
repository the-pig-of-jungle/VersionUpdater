package com.coder.zzq.versionupdater.annotation.compiler;

import com.squareup.javapoet.ClassName;

public class ClassNames {
    public static final ClassName BASE_VERSION_UPDATE_CALLBACK =
            ClassName.get("com.coder.zzq.version_updater.communication",
                    "AbstractVersionUpdateCallback");

    public static final ClassName DETECT_OBSERVER_REGISTER = ClassName.get("com.coder.zzq.version_updater",
            "DetectObserverRegister");
    public static final ClassName CHECK_CONFIG = ClassName.get("com.coder.zzq.version_updater",
            "CheckConfig");

    public static final ClassName DOWNLOAD_EVENT_LIVE_DATA =
            ClassName.get("com.coder.zzq.version_updater.communication",
                    "DownloadEventLiveData");

    public static final ClassName DOWNLOAD_EVENT_VIEW_MODEL =
            ClassName.get("com.coder.zzq.version_updater.communication",
                    "DownloadEventViewModel");

    public static final ClassName VIEW_MODEL_PROVIDER =
            ClassName.get("androidx.lifecycle",
                    "ViewModelProvider");
    public static final ClassName DETECT_OBSERVER_REGISTER_INTERFACE =
            ClassName.get("com.coder.zzq.version_updater.communication",
                    "IDetectObserverRegister");

    public static final ClassName APPLICATION =
            ClassName.get("android.app", "Application");

    public static final ClassName VERSION_UPDATER_INITIALIZER =
            ClassName.get("com.coder.zzq.version_updater",
                    "VersionUpdaterInitializer");

    public static final ClassName TOOLKIT =
            ClassName.get("com.coder.zzq.toolkit", "Toolkit");

    public static final ClassName DETECT_OBSERVER_REGISTER_PROVIDER =
            ClassName.get("com.coder.zzq.version_updater.communication",
                    "DetectObserverRegisterProvider");
}
