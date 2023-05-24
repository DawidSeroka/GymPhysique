package com.myproject.gymphysique.core.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun initReminderChannel(
    context: Context
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationChannel = NotificationChannel(
        NotificationID.REMINDER_NOTIFICATION_CHANNEL_ID,
        "Title",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    notificationManager.createNotificationChannel(notificationChannel)
}

object NotificationID {
    const val REMINDER_NOTIFICATION_CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL_ID"
}
