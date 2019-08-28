package com.smvis123

import android.app.Application
import android.content.Intent
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal
import com.smvis123.helper.PrefManager

class GospelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        /*One Signal*/
        OneSignal.startInit(this)
            .setNotificationOpenedHandler(ExampleNotificationOpenedHandler())
            //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .autoPromptLocation(true)
            .init()
    }

    inner class ExampleNotificationOpenedHandler : OneSignal.NotificationOpenedHandler {
        override fun notificationOpened(result: OSNotificationOpenResult?) {
            val title = result?.notification?.payload?.title
            val desc = result?.notification?.payload?.body
            val image = result?.notification?.payload?.bigPicture
            val pref = PrefManager(applicationContext)
            pref.addPushData(title ?: "", desc ?: "", image ?: "")
            val intent = Intent(applicationContext, MainSplash::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
            applicationContext.startActivity(intent)
        }
    }
}