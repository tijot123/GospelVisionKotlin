package com.smvis123.schedule

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import com.smvis123.R
import java.text.SimpleDateFormat
import java.util.*


class AlarmBroadcastReceiver : BroadcastReceiver() {
    private lateinit var mChannel: NotificationChannel
    private var message: String = ""
    override fun onReceive(context: Context?, intent: Intent?) {
        val currentTime = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm", Locale.ENGLISH)
        val resultDate = Date(currentTime)
        println(sdf.format(resultDate))
        if (intent != null && intent.action == "com.smvis123.ACTION") {
            message = intent.getStringExtra("data")
            val smallIcon = R.drawable.ic_alarm
            val largeIcon = BitmapFactory.decodeResource(
                context!!.resources, R.mipmap.ic_launcher
            )
            val `when` = System.currentTimeMillis()
            val title = (context.resources.getString(R.string.app_name)
                    + " "
                    + "Reminder")
            try {
                // Sets an ID for the notification, so it can be updated.
                val notifyID = 1
                val CHANNEL_ID = context.getString(R.string.app_name)// The id of the channel.
                val name =
                    context.getString(R.string.app_name)// The user-visible name of the channel.
                var importance = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    importance = NotificationManager.IMPORTANCE_HIGH
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                }
                val notification: Notification
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notification = Notification.Builder(context, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(smallIcon)
                        .setLargeIcon(largeIcon)
                        .setAutoCancel(true)
                        .setTicker(title)
                        .setWhen(`when`)
                        .setChannelId(CHANNEL_ID)
                        .build()
                } else {
                    notification = Notification.Builder(context)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(smallIcon)
                        .setLargeIcon(largeIcon)
                        .setAutoCancel(true)
                        .setTicker(title)
                        .setWhen(`when`)
                        .setPriority(Notification.PRIORITY_MAX) // priority only for reminder notifications
                        .build()
                }


                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val attributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                    mChannel.setSound(
                        Uri.parse("android.resource://" + context.packageName + "/" + R.raw.cuckooclock),
                        attributes
                    )
                    mChannel.enableVibration(true)
                    notificationManager.createNotificationChannel(mChannel)
                } else {
                    notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
                    notification.sound =
                        Uri.parse("android.resource://" + context.packageName + "/" + R.raw.cuckooclock)
                }
                notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

                notificationManager.notify(notifyID, notification)

            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
    }
}