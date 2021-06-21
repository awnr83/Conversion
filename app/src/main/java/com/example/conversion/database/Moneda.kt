package com.example.conversion.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_moneda")
data  class Moneda (

    @PrimaryKey(autoGenerate= true)
    val nro: Long= 0L,

    @ColumnInfo
    var nombre: String="",

    @ColumnInfo
    var valor: Double= 0.0
)