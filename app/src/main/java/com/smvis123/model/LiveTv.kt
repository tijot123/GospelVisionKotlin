package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class LiveTv(
    @SerializedName("URL")
    var liveTvList: MutableList<Live>
)

data class Live(
    @SerializedName("youtube_url")
    var youtubeUrl: String,
    @SerializedName("ios_url")
    var androidUrl: String
)