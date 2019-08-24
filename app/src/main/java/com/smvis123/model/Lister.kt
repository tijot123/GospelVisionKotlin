package com.smvis123.model

import java.io.Serializable

data class Lister(
    var galleryList: MutableList<GalleryImages>
) : Serializable