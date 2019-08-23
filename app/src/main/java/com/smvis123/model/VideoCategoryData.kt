package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class VideoCategoryData(
    @SerializedName("video_category")
    var videoCategory: CategoryVideos
)

data class CategoryVideos(
    var videos: MutableList<Videos>
)

data class Videos(
    @SerializedName("url")
    var videoUrl: String,
    var title: String,
    var description: String,
    @SerializedName("thumbnail")
    var imageUrl: String,
    @SerializedName("creation_time")
    var creationDate: String
)