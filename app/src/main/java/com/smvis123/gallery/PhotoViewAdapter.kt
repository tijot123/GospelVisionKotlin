package com.smvis123.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.databinding.AdapterPhotoViewBinding
import com.smvis123.helper.GlideApp
import com.smvis123.model.GalleryImages

class PhotoViewAdapter(private val galleryList: MutableList<GalleryImages>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return galleryList.count()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: AdapterPhotoViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.adapter_photo_view,
            container,
            false
        )

        GlideApp.with(container.context).load(IMAGE_BASE_URL + galleryList[position].path)
            .into(binding.imageView)
        binding.textView.text = galleryList[position].title

        container.addView(binding.root, 0)
        return binding.root
    }
}