package com.smvis123.gallery

import com.smvis123.model.GalleryAlbums

interface GalleryItemClickedListener {
    fun onItemClicked(item: GalleryAlbums)
}