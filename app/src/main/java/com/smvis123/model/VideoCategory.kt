package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class VideoCategory(
    @SerializedName("vidcategories")
    var vidCategoryList: MutableList<Category>
)

data class Category(
    var id: Int,
    var title: String,
    var description: String,
    @SerializedName("image_url")
    var imageUrl: String
)