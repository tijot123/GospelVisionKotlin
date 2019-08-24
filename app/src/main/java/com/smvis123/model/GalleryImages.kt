package com.smvis123.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GalleryImages(
    var title: String, var path: String, var description: String
) : Serializable

data class Photos(
    @SerializedName("photos")
    var imageList: MutableList<GalleryImages>
)

data class Album(
    @SerializedName("album")
    var photo: Photos
)