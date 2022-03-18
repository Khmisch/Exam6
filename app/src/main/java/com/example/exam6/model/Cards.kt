package com.example.exam6.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_table")
data class Cards (
    val cvv: String,
    val full_name: String,
    val exp_date_month: String,
    val exp_date_year: String,
    val card_number: String,
    var is_server:Boolean? = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int ? =null,

)