package com.example.conversion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Moneda::class], version = 4, exportSchema = false)
abstract class MonedaDataBase : RoomDatabase() {

    abstract val monedaDatabaseDao: MonedaDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: MonedaDataBase? = null
        fun getInstance(context: Context): MonedaDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MonedaDataBase::class.java,
                        "datos_history"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}