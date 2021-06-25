package com.example.conversion.convertir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.conversion.database.Moneda
import com.example.conversion.databinding.ListItemsBinding

class CurrencyAdapter(val clickListener: MonedaListener): ListAdapter<Moneda, CurrencyAdapter.ViewHolder>(CurrencyDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(getItem(position), clickListener)
    }

    //clase para list_items
    class ViewHolder private constructor(val binding: ListItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun render(item: Moneda, clickListener: MonedaListener){
            binding.moneda= item
            binding.clickListener= clickListener
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

class MonedaListener(val clickListener:(nro:Long)->Unit) {
    fun onClick(moneda: Moneda)= clickListener(moneda.nro)
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

