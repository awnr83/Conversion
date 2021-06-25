package com.example.conversion.convertir

import androidx.databinding.BindingAdapter
import android.widget.TextView
import com.example.conversion.database.Moneda


@BindingAdapter("setNombreMoneda")
fun TextView.nombreMoneda(item: Moneda?){
    item?.let{
        text=item.nombre
    }
}

@BindingAdapter("setValorMoneda")
fun TextView.valorMoneda(item: Moneda?){
    item?.let{
        text=item.valor.toString()
    }
}
