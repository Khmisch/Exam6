package com.example.exam6.manager

import android.content.Context
import androidx.room.*
import com.example.exam6.model.Cards
import com.example.exam6.database.Dao

@Database(entities = [Cards::class], version = 3)
abstract class RoomManager : RoomDatabase() {

    abstract fun cardDao(): Dao

    companion object {

        private var INSTANCE: RoomManager? = null

        @Synchronized
        fun getInstance(context: Context): RoomManager {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, RoomManager::class.java, "saved_cards.dp")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }

    }

}