package com.myproject.gymphysique.accountsetup.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class RemindersManager @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {
    fun startReminder(
        reminderTime: String = reminderHour,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val (hours, min) = reminderTime.split(":").map { it.toInt() }
        val alarmIntent =
            Intent(applicationContext.applicationContext, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    applicationContext.applicationContext,
                    reminderId,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        val calendar: Calendar = Calendar.getInstance(Locale.GERMANY).apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, min)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    fun stopReminder(
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                applicationContext,
                reminderId,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}

const val reminderHour = "19:00"
const val REMINDER_NOTIFICATION_REQUEST_CODE = 123
