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

class CurrencyAdapter: ListAdapter<Moneda, CurrencyAdapter.ViewHolder>(CurrencyDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(getItem(position))
    }

    //clase para list_items
    class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val textMoneda= itemView.findViewById<TextView>(R.id.textViewMoneda)
        val textValor= itemView.findViewById<TextView>(R.id.textViewValor)

        fun render(item: Moneda){
            textMoneda.text= item.nombre
            textValor.text= item.valor.toString()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val view= layoutInflater.inflate(R.layout.list_items, parent, false)
                return ViewHolder(view)
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

