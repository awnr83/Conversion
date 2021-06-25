package com.example.conversion.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MonedaDataBaseDao {

    @Insert
    fun insertMoneda(moneda: Moneda)

//busquedas
    @Query("select * from table_moneda where nombre= :nom")
    fun getMoneda_nombre(nom: String): Moneda?
    @Query("select * from table_moneda where nro= :nro")
    fun getMoneda_nro(nro: Long): Moneda?

//actualizaciones
    @Update
    fun actualizar(moneda: Moneda)

//listar todos
    @Query("select * from table_moneda order by nombre asc")
    fun getallMonedas(): LiveData<List<Moneda>>

//eliminar
    @Query("delete from table_moneda")
    fun deleteAll()
    @Delete
    fun delete(moneda: Moneda)
}