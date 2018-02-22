package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.PreferenceUtilities;

class ReminderTasks {

    public static String ACTION_INCREMENT_WATER_COUNT;

    public static void executask(Context context, String action) {
        if (action == ACTION_INCREMENT_WATER_COUNT) {
            increamentWaterCount(context);
        }
    }

    private static void increamentWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
    }
}