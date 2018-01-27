package com.coder.zzq.versionupdaterlib;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;

/**
 * Created by pig on 2018/1/27.
 */

public class MessageSender {
    private static EventBus sEventBus;

    private MessageSender() {

    }

    private static EventBus getEventBus() {
        if (sEventBus == null) {
            sEventBus = EventBus.builder()
                        .build();
        }

        return sEventBus;
    }

    public static boolean isRegister(Object subscriber){
        return getEventBus().isRegistered(subscriber);
    }

    public static void register(Object subscriber) {
        getEventBus().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        getEventBus().unregister(subscriber);
    }





    public static void sendMsg(DownloadEvent downloadEvent){
        getEventBus().postSticky(downloadEvent);
    }
}
