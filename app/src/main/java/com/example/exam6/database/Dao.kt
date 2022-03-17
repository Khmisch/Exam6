package com.example.pinterest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exam6.model.CardElement
import com.example.exam6.model.Cards

@Dao
interface Dao {

    @Insert
    fun savePhoto(pins: Cards)

    @Query("SELECT * FROM cards_table")
    fun getAllSavedPhotos(): List<Cards>

    @Query("DELETE FROM cards_table")
    fun clearSavedPhotos()

    @Query("DELETE FROM cards_table WHERE id=:id")
    fun removeSavedPhotos(id: Int)

}