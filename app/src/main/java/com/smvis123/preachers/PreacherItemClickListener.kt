package com.smvis123.preachers

import android.widget.ImageView
import com.smvis123.model.Preachers

interface PreacherItemClickListener {
    fun onItemClicked(preacher: Preachers, imageView: ImageView)
}