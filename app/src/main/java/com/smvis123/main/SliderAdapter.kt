package com.smvis123.main

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.databinding.AdapterSliderBinding
import com.smvis123.helper.GlideApp
import com.smvis123.helper.sendWebIntent
import com.smvis123.model.Slider

class SliderAdapter(private val item: MutableList<Slider>) : PagerAdapter() {


    override fun getCount(): Int {
        return item.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: AdapterSliderBinding =
            DataBindingUtil.inflate(inflater, R.layout.adapter_slider, container, false)
        GlideApp.with(container.context).load(IMAGE_BASE_URL + item[position].imageUrl)
            .into(binding.imageView)
        binding.imageView.setOnClickListener {
            if (!TextUtils.isEmpty(item[position].redirectUri)) {
                sendWebIntent(container.context, item[position].title, item[position].redirectUri)
            }
        }

        val viewPager = container as androidx.viewpager.widget.ViewPager
        viewPager.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as androidx.viewpager.widget.ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}