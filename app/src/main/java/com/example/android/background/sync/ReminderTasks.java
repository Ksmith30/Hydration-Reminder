package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.NotificationUtils;
import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {

    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String ACTION_ISSUE_CHARGING_REMINDER = "issue-charging-reminder";

    public static void executeTask(Context context, String action) {
        if (action.equals(ACTION_INCREMENT_WATER_COUNT)) {
            incrementWaterCount(context);
        } else if (action.equals(ACTION_DISMISS_NOTIFICATION)) {
            dismissNotification(context);
        } else if (action.equals(ACTION_ISSUE_CHARGING_REMINDER)) {
            issueChargingReminder(context);
        }
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
    }

    private static void dismissNotification(Context context) {
        NotificationUtils.clearAllNotifications(context);
    }

    private static void issueChargingReminder(Context context) {
        NotificationUtils.remindUserBecauseCharging(context);
        PreferenceUtilities.getChargingReminderCount(context);
        executeTask(context, ACTION_ISSUE_CHARGING_REMINDER);
    }
}