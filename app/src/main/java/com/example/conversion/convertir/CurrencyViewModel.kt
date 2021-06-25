package com.example.conversion.convertir

import android.app.Application
import androidx.lifecycle.*
import com.example.conversion.database.MonedaDataBaseDao
import com.example.conversion.formatMonedasToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CurrencyViewModel(val dataBase: MonedaDataBaseDao, application: Application): AndroidViewModel(application) {

    private val jobViewModel= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main + jobViewModel)
    override fun onCleared() {
        super.onCleared()
        jobViewModel.cancel()
    }
//variables editText---------------------------------------
    //campo EditText
    var pesos= MutableLiveData<String>()
    var nro= MutableLiveData<String>()

    //campos encapsulado
    private val _resultado= MutableLiveData<String>()
    val resultado: LiveData<String>
        get()= _resultado

    init {
        _resultado.value="0.0"
        pesos.value="0.0"
        nro.value="-"
    }
//------ Todas las monedas Cargadas
    val allMonedas= dataBase.getallMonedas()
    var allMonedasString=  Transformations.map(allMonedas){ formatMonedasToString(it, application.resources) }
//    var allMonedasString= if(allMonedas.value.isNullOrEmpty()) {
//        "No hay datos"
//        }else{
//            Transformations.map(allMonedas){ formatMonedasToString(it, application.resources) }
//        }


}