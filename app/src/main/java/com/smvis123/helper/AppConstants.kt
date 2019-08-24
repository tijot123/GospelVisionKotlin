package com.smvis123.helper

import android.content.Context
import android.content.Intent
import com.smvis123.web.WebActivity

const val WEB_URl = "web"
const val WEB_TITLE = "web_title"
const val SLIDER_DELAY: Long = 4000
const val SLIDER_PERIOD: Long = 6000
const val VIDEO_URl = "video_url"
const val VIDEO_CATEGORY_ID = "video_category_id"
const val GOSPEL_WEBSITE = "https://www.gospelvision.tv/"
const val GOSPEL_FACEBOOK = "https://www.facebook.com/GospelVisionTV/"
const val FACEBOOK = "Facebook"
const val WEBSITE = "Website"
const val GALLERY_IMAGES = "images"
const val PREACHER_DATA = "preacher"
const val POSITION = "position"
const val REQUEST_CODE_UPDATE: Int = 1201


fun sendWebIntent(context: Context, title: String, webUrl: String) {
    val intent = Intent(context, WebActivity::class.java)
    intent.putExtra(WEB_URl, webUrl)
    intent.putExtra(WEB_TITLE, title)
    context.startActivity(intent)
}