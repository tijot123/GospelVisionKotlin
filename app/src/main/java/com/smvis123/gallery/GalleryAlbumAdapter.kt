package com.smvis123.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.databinding.AdapterGalleryAlbumsBinding
import com.smvis123.helper.GlideApp
import com.smvis123.model.GalleryAlbums

class GalleryAlbumAdapter(
    private val albumsList: MutableList<GalleryAlbums>,
    private val galleryItemClickedListener: GalleryItemClickedListener
) :
    RecyclerView.Adapter<GalleryAlbumAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: AdapterGalleryAlbumsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding: AdapterGalleryAlbumsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_gallery_albums, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumsList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        GlideApp.with(holder.itemView.context).load(IMAGE_BASE_URL + albumsList[position].path)
            .into(holder.binding.imageView)
        holder.binding.textView.text = albumsList[position].title
        holder.itemView.setOnClickListener {
            galleryItemClickedListener.onItemClicked(albumsList[holder.adapterPosition])
        }
    }
}