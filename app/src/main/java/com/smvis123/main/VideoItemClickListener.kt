package com.smvis123.main

import com.smvis123.model.Category

interface VideoItemClickListener {
    fun onVideoItemClicked(videoCategory: Category)
}