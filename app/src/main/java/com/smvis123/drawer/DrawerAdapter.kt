package com.smvis123.drawer

import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smvis123.R
import com.smvis123.databinding.AdapterDrawerBinding

class DrawerAdapter(
    private val drawerTitles: Array<String>, private val drawerImages: TypedArray,
    private val drawerItemClickListener: DrawerItemClickListener
) :
    RecyclerView.Adapter<DrawerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: AdapterDrawerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: AdapterDrawerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.adapter_drawer, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return drawerTitles.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 4) {
            holder.binding.title.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.binding.imageView.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                ), PorterDuff.Mode.SRC_ATOP
            )
        }
        holder.binding.title.text = drawerTitles[position]
        holder.binding.imageView.setImageDrawable(drawerImages.getDrawable(position))
        holder.itemView.setOnClickListener {
            drawerItemClickListener.onItemClicked(holder.adapterPosition)
        }
    }

}