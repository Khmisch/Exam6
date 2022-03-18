package com.example.exam6.network.service

import com.example.exam6.model.Cards
import retrofit2.Call
import retrofit2.http.*


interface CardService {

    @GET("Card")
    fun getCards(): Call<ArrayList<Cards>>

    @POST("Card")
    fun addCard(@Body card: Cards): Call<Cards>

    @DELETE("Card/{id}")
    fun deleteCard(@Path("id") id: Int): Call<Cards>
}