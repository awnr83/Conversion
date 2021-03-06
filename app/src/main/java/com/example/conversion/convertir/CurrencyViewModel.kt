package com.example.conversion.convertir

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.conversion.database.Moneda
import com.example.conversion.database.MonedaDataBaseDao
import com.example.conversion.formatMonedasToString
import kotlinx.coroutines.*

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

    //campos encapsulado
    private val _resultado= MutableLiveData<String>()
    val resultado: LiveData<String>
        get()= _resultado

    //nombre de moneda elegida
    private val _monedaString= MutableLiveData<String>()
    val monedaString: LiveData<String>
        get()=_monedaString

    //aviso no ingreso valor a convertir
    private val _dato= MutableLiveData<Boolean>()
    val dato: LiveData<Boolean>
        get()=_dato
    fun doneDato(){
        _dato.value=false
    }


//------ Todas las monedas Cargadas
    val allMonedas= dataBase.getallMonedas()

    //click en una moneda
    fun onMonedaClicked(id: Long) {
        var moneda=Moneda()
        if (!pesos.value.isNullOrEmpty()) {
            uiScope.launch {
                moneda= getToMonedaFromdataBase(id)
                _monedaString.value = moneda.nombre
                _resultado.value= String.format("%.3f",((pesos.value!!.toDouble()).div(moneda.valor)))
            }
        } else
            _dato.value = true
    }
    private suspend fun getToMonedaFromdataBase(id: Long): Moneda{ //se inicia otro hilo
        return withContext(Dispatchers.IO){
            var moneda= dataBase.getMoneda_nro(id)
            moneda!!
        }
    }

    init {
        _monedaString.value=""
        _resultado.value=""
        _dato.value= false
    }
}