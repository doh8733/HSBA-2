package com.beetech.hsba.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetech.hsba.R
import com.beetech.hsba.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.beetech.hsba.entity.medical_history.MedicalHistory
import com.beetech.hsba.extension.inflate
import com.beetech.hsba.utils.Define
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_medical_history_adapter.view.*

class MedicalHistoryAdapter(
    context: Context,
    val onClickItem: (com.beetech.hsba.entity.medical_history.MedicalHistory) -> Unit
) : EndlessLoadingRecyclerViewAdapter(context, false) {
    class MedicalViewholder(view: View) : NormalViewHolder(view) {
        fun onBindViews(mediacalHistory: MedicalHistory) {
            // init UI
            itemView.imb_more.visibility = View.INVISIBLE
            itemView.tv_specialty_or_services.text = mediacalHistory.serviceSpecialtyName
            itemView.tv_time.text = mediacalHistory.dateMedicalHistories
            itemView.tv_name_doctor.text = mediacalHistory.doctorName
            itemView.tv_title.text = mediacalHistory.position
            Glide.with(itemView.context).load(Define.Link.LINK_IMG + mediacalHistory.avatar)
                .placeholder(R.drawable.avatar).into(itemView.img_doctor)
        }
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return MedicalViewholder(parent.inflate(R.layout.item_medical_history_adapter))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val item = getItem(position,MedicalHistory::class.java)
        item?.let {
            holder.itemView.apply {
                (holder as MedicalViewholder).onBindViews(it)
            }
        }
        holder.itemView.setOnClickListener {
            holder.itemView.imb_more.visibility = View.VISIBLE
        }
    }
}