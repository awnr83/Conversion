package com.example.conversion.convertir

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conversion.R
import com.example.conversion.database.Moneda

class CurrencyAdapter:RecyclerView.Adapter<CurrencyAdapter.TextViewViewHolder>() {
    var data = listOf<Moneda>()
    set(value) {
        field= value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val view= layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewViewHolder, position: Int) {
        holder.textView.text= "moneda: ${data[position].nombre} valor: ${data[position].valor}"
    }

    override fun getItemCount()= data.size


    //class de elemento a mostrar
    class TextViewViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
}

