package com.smvis123.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.databinding.AdapterVideoCategoryBinding
import com.smvis123.helper.GlideApp
import com.smvis123.model.Category

class VideoCategoryAdapter(
    private val videoCatList: MutableList<Category>
    , private val videoItemClickListener: VideoItemClickListener
) :
    RecyclerView.Adapter<VideoCategoryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdapterVideoCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_video_category, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoCatList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.videoDesc.text = videoCatList[position].description
        holder.binding.videoTitle.text = videoCatList[position].title
        GlideApp.with(holder.itemView.context)
            .load(IMAGE_BASE_URL + videoCatList[position].imageUrl).into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            videoItemClickListener.onVideoItemClicked(videoCatList[holder.adapterPosition])
        }
    }

    class MyViewHolder(val binding: AdapterVideoCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}