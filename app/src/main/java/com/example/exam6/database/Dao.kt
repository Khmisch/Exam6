package com.example.pinterest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exam6.model.Cards

@Dao
interface Dao {

    @Insert
    fun saveCard(pins: Cards)

    @Query("SELECT * FROM cards_table")
    fun getAllSavedCard(): List<Cards>

    @Query("UPDATE cards_table SET is_server=:isAdded WHERE id=:id")
    fun changeIsAdded(id: Int, isAdded: Boolean)

}