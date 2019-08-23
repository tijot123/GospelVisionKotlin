package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class ImageSlider(
    @SerializedName("image_sliders")
    var sliderList: MutableList<Slider>
)

data class Slider(
    @SerializedName("pid")
    var id: String,
    var title: String,
    @SerializedName("path")
    var imageUrl: String,
    @SerializedName("url")
    var redirectUri: String
)