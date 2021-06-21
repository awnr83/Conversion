package com.example.conversion.convertir

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.conversion.database.MonedaDataBaseDao
import java.lang.IllegalArgumentException

class CurrencyViewModelFactory(private val dataSource: MonedaDataBaseDao,
                               private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyViewModel::class.java)){
            return CurrencyViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("No se pudo crear el ViewModel")
    }
}