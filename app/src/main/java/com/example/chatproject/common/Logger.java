package com.example.chatproject.common;

import android.util.Log;

public class Logger {
    private static final String APP_TAG = "Chatting";

    public static void d(String tag, String msg) {
        Log.d(APP_TAG, "[" + tag + "]" + msg);
    }

    public static void e(String tag, String msg) {
        Log.e(APP_TAG, "[" + tag + "]" + msg);
    }
}
