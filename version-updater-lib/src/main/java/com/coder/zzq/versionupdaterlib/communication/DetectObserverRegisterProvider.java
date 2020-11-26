package com.coder.zzq.versionupdaterlib.communication;

public class DetectObserverRegisterProvider {
    private static IDetectObserverRegister sDetectObserverRegister;

    public static void setDetectObserverRegister(IDetectObserverRegister detectObserverRegister) {
        sDetectObserverRegister = detectObserverRegister;
    }

    public static IDetectObserverRegister getDetectObserverRegister(){
        return sDetectObserverRegister;
    }
}
