package com.smvis123.programs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.databinding.AdapterVideoProgramsBinding
import com.smvis123.helper.GlideApp
import com.smvis123.model.Videos
import java.text.SimpleDateFormat
import java.util.*

class VideoProgramsAdapter(
    private val videosList: MutableList<Videos>,
    private val videoClickedListener: VideoClickedListener
) :
    RecyclerView.Adapter<VideoProgramsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: AdapterVideoProgramsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding: AdapterVideoProgramsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_video_programs, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videosList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        GlideApp.with(holder.itemView.context).load(videosList[position].imageUrl)
            .into(holder.binding.imageView)
        holder.binding.videoTitle.text = videosList[position].title
        holder.binding.videoDate.text = formatDate(videosList[position].creationDate)

        holder.itemView.setOnClickListener {
            videoClickedListener.onVideoClicked(videosList[holder.adapterPosition])
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val targetFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
            val date = originalFormat.parse(dateString)
            targetFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }

    }
}