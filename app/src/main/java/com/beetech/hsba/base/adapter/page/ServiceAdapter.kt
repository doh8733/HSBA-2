package com.beetech.hsba.base.adapter.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.entity.services.Services
import com.beetech.hsba.entity.specialty.Specialty
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_chuyen_khoa.view.*

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.TabPageHolder>() {

    var lPhoto = mutableListOf<Services>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class TabPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(specialty: Services) {
            Glide.with(itemView.context)
                .load("http://hsba-v2.beetechdev.vn:1680/storage/" + specialty.icon)
                .placeholder(R.drawable.ic_gan).into(itemView.img_icon)
            itemView.tv_text.text = specialty.name.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabPageHolder {
        return TabPageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chuyen_khoa, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TabPageHolder, position: Int) {
        val photos = lPhoto[position]
        holder.onBind(photos)
        if (position == lPhoto.lastIndex) {
            holder.itemView.img_dots.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return lPhoto.size
    }


}