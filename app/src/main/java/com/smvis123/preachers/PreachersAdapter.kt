package com.smvis123.preachers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.databinding.AdapterPreachersBinding
import com.smvis123.helper.GlideApp
import com.smvis123.model.Preachers

class PreachersAdapter(
    private val pastorList: MutableList<Preachers>,
    private val preacherItemClickListener: PreacherItemClickListener
) :
    RecyclerView.Adapter<PreachersAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: AdapterPreachersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding: AdapterPreachersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_preachers,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pastorList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        GlideApp.with(holder.itemView.context).load(IMAGE_BASE_URL + pastorList[position].thumbnail)
            .into(holder.binding.imageView)
        holder.binding.videoDesc.text = pastorList[position].designation
        holder.binding.videoTitle.text = pastorList[position].name
        holder.itemView.setOnClickListener {
            preacherItemClickListener.onItemClicked(
                pastorList[holder.adapterPosition],
                holder.binding.imageView
            )
        }
    }

}