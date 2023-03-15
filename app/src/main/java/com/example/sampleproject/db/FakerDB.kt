package com.example.sampleproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampleproject.models.Products

@Database(entities = [Products::class], version = 1, exportSchema = false)

abstract class FakerDB : RoomDatabase() {

    abstract fun getFakerDAO(): FakerDAO

    companion object {
        private var INSTANCE: FakerDB? = null
        fun getDatabase(context: Context): FakerDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        FakerDB::class.java,
                        "FakerDB_DATABASE"
                    )
                        .createFromAsset("FakerDB.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}