package com.example.conversion.money

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.conversion.database.MonedaDataBaseDao

class MoneyViewModelFactory(private val dataSource: MonedaDataBaseDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoneyViewModel::class.java))
            return MoneyViewModel(dataSource) as T
        throw IllegalArgumentException("No se pudo crear ViewModel")
    }
}