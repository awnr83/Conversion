package com.example.conversion.convertir

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.conversion.R
import com.example.conversion.database.Moneda
import com.example.conversion.databinding.ListItemsBinding

class CurrencyAdapter: ListAdapter<Moneda, CurrencyAdapter.ViewHolder>(CurrencyDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(getItem(position))
    }

    //clase para list_items
    class ViewHolder private constructor(val binding: ListItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun render(item: Moneda){
            binding.moneda= item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= ListItemsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

//DiffUtil
class CurrencyDiffUtilCallback:DiffUtil.ItemCallback<Moneda>(){
    override fun areItemsTheSame(oldItem: Moneda, newItem: Moneda): Boolean {
        return oldItem.nro == newItem.nro
    }
    override fun areContentsTheSame(oldItem: Moneda, newItem: Moneda): Boolean {
        return oldItem == newItem
    }
}

