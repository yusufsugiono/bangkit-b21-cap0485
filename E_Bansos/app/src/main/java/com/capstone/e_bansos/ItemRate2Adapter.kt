package com.capstone.e_bansos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.e_bansos.ListRate1
import com.capstone.e_bansos.databinding.ItemRate1Binding
import com.capstone.e_bansos.databinding.ItemRate2Binding

class ItemRate2Adapter(private val listRate2: ArrayList<ListRate2>) : RecyclerView.Adapter<ItemRate2Adapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemRate1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: ListRate2) {
            with(binding){

                tvLocation.text = hero.location
                tvTotalpositive.text = hero.total
                tvDatetime.text = hero.date

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRate1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRate2[position])
    }

    override fun getItemCount(): Int = listRate2.size


}