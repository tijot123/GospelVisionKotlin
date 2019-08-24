package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class GalleryAlbums(
    var id: String,
    var title: String,
    var path: String,
    var description: String
)

data class GalleryAlbumsResponse(
    @SerializedName("albums")
    var galleryAlbumsList: MutableList<GalleryAlbums>
)