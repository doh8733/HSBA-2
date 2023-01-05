package com.beetech.hsba.base.adapter.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.entity.ChuyenKhoa
import kotlinx.android.synthetic.main.item_chuyen_khoa.view.*

class TabPageAdapter : RecyclerView.Adapter<TabPageAdapter.TabPageHolder>() {

    var lPhoto = mutableListOf<ChuyenKhoa>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class TabPageHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(chuyenkhoa: ChuyenKhoa){
            itemView.img_icon.setImageResource(chuyenkhoa.img)
            itemView.tv_text.text = chuyenkhoa.text.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabPageHolder {
        return TabPageHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_chuyen_khoa,parent,false))
    }

    override fun onBindViewHolder(holder: TabPageHolder, position: Int) {
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