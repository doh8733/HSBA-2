package com.beetech.hsba.base.adapter.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.entity.specialty.Specialty
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_chuyen_khoa.view.*

class SpecialistAdapter : RecyclerView.Adapter<SpecialistAdapter.SpecialistAdapter>() {

    var lPhoto = mutableListOf<Specialty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SpecialistAdapter(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(specialty: Specialty){
            Glide.with(itemView.context).load("http://hsba-v2.beetechdev.vn:1680/storage/"+specialty.icon).into(itemView.img_icon)
            itemView.tv_text.text = specialty.name.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistAdapter {
        return SpecialistAdapter( LayoutInflater.from(parent.context).inflate(R.layout.item_chuyen_khoa,parent,false))
    }

    override fun onBindViewHolder(holder: SpecialistAdapter, position: Int) {
        val photos = lPhoto[position]
        holder.onBind(photos)
        if (position == lPhoto.lastIndex){
            holder.itemView.img_dots.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return lPhoto.size
    }


}