package com.example.android.background.utilities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.lang.String;

import com.example.android.background.MainActivity;
import com.example.android.background.R;
import com.example.android.background.sync.ReminderTasks;
import com.example.android.background.sync.WaterReminderIntentService;

public class NotificationUtils {

    private static final int NOTIFICATION_REQUEST = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 239;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 298;

    private static final String CHANNEL_ID = "channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
            @SuppressLint("WrongConstant") NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hello"))
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT <=
                Build.VERSION_CODES.O) {
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(NOTIFICATION_REQUEST, mBuilder.build());
    }

    public static PendingIntent contentIntent(Context context) {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, NOTIFICATION_REQUEST,
                mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static Bitmap largeIcon(Context context) {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, R.drawable.ic_local_drink_black_24px);
    }

    public static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, WaterReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,
                context.getString(R.string.action_dismiss_notification), ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }


    public static NotificationCompat.Action drinkWaterAction(Context context) {
        Intent drinkWaterIntent = new Intent(context, WaterReminderIntentService.class);
        drinkWaterIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent drinkWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                drinkWaterIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action drinkWaterReminderAction = new NotificationCompat.Action(
                R.drawable.ic_drink_notification,
                context.getString(R.string.action_drink_water),
                drinkWaterPendingIntent);

        return drinkWaterReminderAction;

    }

}
