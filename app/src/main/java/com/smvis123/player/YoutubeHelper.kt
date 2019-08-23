package com.smvis123.player

import android.net.Uri
import java.util.regex.Pattern

object YoutubeHelper {
    fun getYouTubeVideoId(videoUrl: String?): String? {

        if (videoUrl != null && videoUrl.isNotEmpty()) {

            val videoUri = Uri.parse(videoUrl)
            var videoId = videoUri.getQueryParameter("v")

            if (videoId == null)
                videoId = parseYoutubeVideoId(videoUrl)

            return videoId
        }
        return null
    }

    private fun parseYoutubeVideoId(youtubeUrl: String?): String? {
        var videoId: String? = null
        if ((youtubeUrl != null && youtubeUrl.trim { it <= ' ' }.isNotEmpty()
                    && youtubeUrl.startsWith("http"))
        ) {
            // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
            val expression = ("^.*((youtu.be"
                    + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*")
            val pattern = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            )
            val matcher = pattern.matcher(youtubeUrl)
            if (matcher.matches()) {
                // Regular expression some how doesn't work with id with "v" at
                // prefix
                val groupIndex1 = matcher.group(7)
                if (groupIndex1 != null && groupIndex1.length == 11)
                    videoId = groupIndex1
                else if (groupIndex1 != null && groupIndex1.length == 10)
                    videoId = "v$groupIndex1"
            }
        }
        return videoId
    }
}