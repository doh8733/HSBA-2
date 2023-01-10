package com.beetech.hsba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.entity.slider.Photo
import kotlinx.android.synthetic.main.item_slider.view.*

class ImageSlideAdapter : RecyclerView.Adapter<ImageSlideAdapter.ImageSliderViewHolder>() {

    var listPhoto = mutableListOf<Photo>()
        set(value) {
            field = value
        }

    class ImageSliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(photo: Photo){
            itemView.img_slide.setImageResource(photo.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_slider,parent,false))
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val photos = listPhoto[position]
        holder.onBind(photos)
    }

    override fun getItemCount(): Int {
        return listPhoto.size
    }


}