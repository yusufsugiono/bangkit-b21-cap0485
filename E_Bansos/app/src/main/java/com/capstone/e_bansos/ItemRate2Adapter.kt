package com.capstone.e_bansos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.e_bansos.databinding.ItemRate1Binding

class ItemRate2Adapter : RecyclerView.Adapter<ItemRate2Adapter.ListViewHolder>() {
    private val mData = ArrayList<Data>()

    fun setData(items: ArrayList<Data>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemRate1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            with(binding){

                tvLocation.text = data.kecamatan
                tvTotalpositive.text = data.prediksi
                tvDatetime.text = data.tanggal

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRate1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size


}