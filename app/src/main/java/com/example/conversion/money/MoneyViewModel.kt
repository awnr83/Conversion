package com.example.conversion.money

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.conversion.database.Moneda
import com.example.conversion.database.MonedaDataBaseDao
import kotlinx.coroutines.*


class MoneyViewModel(val database: MonedaDataBaseDao): ViewModel() {

    private val jobViewModel= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main + jobViewModel)
    override fun onCleared() {
        super.onCleared()
        jobViewModel.cancel()
    }

//variables--------------------------------------------------
    private var nro:Long=0
    var editMoneda= MutableLiveData<String>()
    var editValor= MutableLiveData<String>()

//alertas y habilitados!-------------------------------------

    private val _alertaDatos= MutableLiveData<Boolean>()
    val alertaDatos: LiveData<Boolean>
        get()= _alertaDatos
    fun doneAlerta(){
        _alertaDatos.value=false
    }
    private val _alertaAgregado= MutableLiveData<Boolean>()
    val alertaAgregado: LiveData<Boolean>
        get()= _alertaAgregado
    fun doneAgregado(){
        _alertaAgregado.value=false
    }
    private val _alertaModificado= MutableLiveData<Boolean>()
    val alertaModificado: LiveData<Boolean>
        get()= _alertaModificado
    fun doneModificado(){
        _alertaModificado.value=false
    }
    private val _alertaEliminado= MutableLiveData<Boolean>()
    val alertaEliminado: LiveData<Boolean>
        get()= _alertaEliminado
    fun doneEliminado(){
        _alertaEliminado.value=false
    }
    //habilitados
    private val _enabledBuscar= MutableLiveData<Int>()
    val enabledBuscar: LiveData<Int>
        get()= _enabledBuscar
    fun doneBuscar(){
        _enabledBuscar.value=View.GONE
    }
    private val _enabledAgregar= MutableLiveData<Int>()
    val enabledAgregar: LiveData<Int>
        get()= _enabledAgregar
    fun doneAgregar(){
        _enabledAgregar.value=View.GONE
    }
    private val _enabledModificar= MutableLiveData<Int>()
    val enabledModificar: LiveData<Int>
        get()= _enabledModificar
    fun doneModificar(){
        _enabledModificar.value=View.GONE
    }
    private val _enabledEliminar= MutableLiveData<Int>()
    val enabledEliminar: LiveData<Int>
        get()= _enabledEliminar
    fun doneEliminar(){
        _enabledEliminar.value=View.GONE
    }
    private val _enabledValor= MutableLiveData<Int>()
    val enabledValor: LiveData<Int>
        get()= _enabledValor
    fun doneValor(){
        _enabledValor.value=View.VISIBLE
    }
    private val _editableNombre= MutableLiveData<Boolean>()
    val editableNombre: LiveData<Boolean>
        get() = _editableNombre

//DB----------------------------------------------------------
    fun onBuscar(){
        if(datosOk()){
            uiScope.launch {
                var moneda= buscarMoneda()

                if (moneda!= null) {//si existe el registro se cargan los valores
                    _editableNombre.value=false
                    _enabledBuscar.value = View.GONE
                    _enabledAgregar.value = View.GONE
                    _enabledValor.value = View.VISIBLE
                    _enabledModificar.value = View.VISIBLE
                    _enabledEliminar.value = View.VISIBLE

                    nro=moneda.nro
                    editValor.value= moneda.valor.toString()
                } else {//se habilita los campos de carga de una nueva moneda
                    _editableNombre.value=true
                    _enabledBuscar.value = View.GONE
                    _enabledEliminar.value = View.GONE

                    _enabledValor.value = View.VISIBLE
                    _enabledAgregar.value = View.VISIBLE
                }
            }
        }
    }
    private fun datosOk():Boolean{
        if(editMoneda.value.isNullOrEmpty()){
            _alertaDatos.value=true
            return false
        }
        return true
    }
    private suspend fun buscarMoneda():Moneda?{
        return withContext(Dispatchers.IO) {
            var moneda = database.getMoneda_nombre(editMoneda.value.toString())
            if(moneda?.nombre=="")
                moneda=null
            moneda
        }
    }

    fun onAgregar(){
        if(!editValor.value.isNullOrEmpty()) {
            uiScope.launch {
                insert()
                editMoneda.value = ""
                editValor.value = ""

                _enabledBuscar.value = View.VISIBLE
                _enabledAgregar.value = View.GONE
                _enabledValor.value = View.GONE

                _alertaAgregado.value = true
                _editableNombre.value=true
            }
        }
    }
    private suspend fun insert(){
        withContext(Dispatchers.IO) {
            var moneda = Moneda(nombre = editMoneda.value.toString(), valor = editValor.value!!.toDouble())
            database.insertMoneda(moneda)
        }
    }

    fun onModificar(){
        if(!editValor.value.isNullOrEmpty()) {
            uiScope.launch {
                update()
                editMoneda.value = ""
                editValor.value = ""
                _enabledBuscar.value = View.VISIBLE
                _enabledValor.value = View.GONE
                _enabledModificar.value = View.GONE
                _enabledEliminar.value = View.GONE

                _alertaModificado.value = true
                _editableNombre.value=true
            }
        }
    }
    private suspend fun update(){
        withContext(Dispatchers.IO) {
            var moneda = Moneda(nro = nro,nombre = editMoneda.value.toString(), valor = editValor.value!!.toDouble())
            Log.i("alfredo","${moneda.nro} - ${moneda.nombre} - ${moneda.valor}")
            database.actualizar(moneda)
        }
    }

    fun onEliminar(){
        uiScope.launch {
            delete()

            editMoneda.value=""
            editValor.value=""
            _enabledBuscar.value = View.VISIBLE
            _enabledValor.value = View.GONE
            _enabledModificar.value = View.GONE
            _enabledEliminar.value = View.GONE

            _alertaEliminado.value=true
            _editableNombre.value=true
        }
    }
    private suspend fun delete(){
        withContext(Dispatchers.IO) {
            var moneda= Moneda(nro=nro,nombre = editMoneda.toString(), valor = editValor.value!!.toDouble())
            database.delete(moneda)
        }
    }

    init {
        editMoneda.value=""
        editValor.value=""

        _alertaDatos.value=false
        _alertaAgregado.value=false
        _alertaModificado.value=false
        _alertaEliminado.value=false

        _enabledBuscar.value=View.VISIBLE
        _enabledValor.value=View.GONE
        _enabledAgregar.value=View.GONE
        _enabledEliminar.value=View.GONE
        _enabledModificar.value=View.GONE

        _editableNombre.value=true
    }

}