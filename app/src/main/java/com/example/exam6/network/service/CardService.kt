package com.example.pinterest.network.service

import com.example.exam6.model.Cards
import retrofit2.Call
import retrofit2.http.*


interface CardService {

    @GET("Card")
    fun getCards(): Call<ArrayList<Cards>>

    @POST("Card")
    fun addCard(@Body card: Cards): Call<Cards>
}