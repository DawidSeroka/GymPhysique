package com.myproject.gymphysique.accountsetup.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.myproject.gymphysique.accountsetup.R
import com.myproject.gymphysique.core.common.NotificationID
import com.myproject.gymphysique.core.model.UserData
import com.myproject.gymphysqiue.core.domain.app.ObserveIfDailyMeasurementExistUseCase
import com.myproject.gymphysqiue.core.domain.settings.GetUserUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var observeIfDailyMeasurementExistUseCase: ObserveIfDailyMeasurementExistUseCase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager

        GlobalScope.launch {
            if (!observeIfDailyMeasurementExistUseCase()) {
                notificationManager.sendReminderNotification(
                    applicationContext = context,
                    channelId = NotificationID.REMINDER_NOTIFICATION_CHANNEL_ID,
                    getUserUseCase()
                )
            }
        }
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
    userData: UserData
) {
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(applicationContext.getString(R.string.daily_reminder))
        .setContentText(
            applicationContext.getString(R.string.hi) + " " + userData.firstName + ", " +
                applicationContext.getString(R.string.daily_measurement_reminder)
        )
        .setSmallIcon(R.drawable.splash_icon)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

const val NOTIFICATION_ID = 1
