package com.smvis123.helper

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor: SharedPreferences.Editor = pref.edit()


    fun addPushData(title: String, desc: String, image: String) {
        editor.putString(PUSH_TITLE, title).putString(PUSH_MSG, desc)
            .putString(PUSH_IMAGE, image).apply()
    }

    fun getPushData(): Array<String?> {
        val data = arrayOfNulls<String>(3)
        data[0] = pref.getString(PUSH_TITLE, "")
        data[1] = pref.getString(PUSH_MSG, "")
        data[2] = pref.getString(PUSH_IMAGE, "")
        return data
    }


}