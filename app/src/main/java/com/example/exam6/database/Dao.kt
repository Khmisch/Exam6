package com.example.exam6.database

import androidx.room.*
import androidx.room.Dao
import com.example.exam6.model.Cards

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCard(cards: Cards)

    @Query("SELECT * FROM cards_table")
    fun getCards(): List<Cards>

    @Query("SELECT * FROM cards_table where is_server=0")
    fun getOfflineCards(): List<Cards>

    @Update
    fun update(card: Cards)

}