package com.example.exam6.model

typealias Card = ArrayList<CardElement>

data class CardElement (
    val cvv: String,
    val full_name: String,
    val exp_date_month: String,
    val exp_date_year: String,
    val card_number: String,
    val id: String
)