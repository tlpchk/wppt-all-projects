package com.appteam.catchup.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color.argb
import android.os.Build
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.appteam.catchup.model.Event
import com.appteam.catchup.MainActivity
import com.appteam.catchup.R
import com.appteam.catchup.TAG
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlin.math.max

object NotificationService {
    const val CHANNEL_ID = "catchup_events"

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        // NOTE: Calling this multiple times does not create other channels if one is already created.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.channel_name)
            val descriptionText = context.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(
        context: Context,
        notificationId: Int,
        event: Event
    ) {
        event.let {
            Log.d("am2019", event.toString())
            val delay: Long = max(0, (it.dateStart.seconds - Timestamp.now().seconds) * 1000)
            Log.d("am2019", "Notification for ${it.title} in ${delay}ms")
            scheduleNotification(context, notificationId, delay, it.title!!, it.description)
        }
    }

    fun scheduleNotification(
        context: Context,
        notificationId: Int,
        delay: Long, //delay is after how much time(in millis) from current time you want to schedule the notification
        title: String,
        contentText: String
    ) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.catchup_small_icon)
            .setColor(argb(255,76,175, 80))
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val intent = Intent(context, MainActivity::class.java)
        val activity =
            PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(activity)

        val notification = builder.build()

        val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent =
            PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)

        Log.i(TAG, "notificationService::scheduleNotification")
    }
}