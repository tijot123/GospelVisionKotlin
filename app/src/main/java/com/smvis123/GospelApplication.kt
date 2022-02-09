package com.smvis123

import android.app.Application
import android.content.Intent
import com.onesignal.OneSignal
import com.smvis123.helper.PrefManager

class GospelApplication : Application() {
    private val oneSignalAppId ="7c0ba973-0294-48f7-bc97-547268b90485"
    override fun onCreate() {
        super.onCreate()
        /*One Signal*/
        OneSignal.initWithContext(this)
        OneSignal.setAppId(oneSignalAppId)
        OneSignal.OSNotificationOpenedHandler { result ->
            val title = result?.notification?.title
            val desc = result?.notification?.body
            val image = result?.notification?.bigPicture
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