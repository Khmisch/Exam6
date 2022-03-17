package com.example.pinterest.network.service

import com.example.exam6.model.Card
import com.example.exam6.model.CardElement
import com.example.exam6.model.CardResp
import retrofit2.Call
import retrofit2.http.*


interface CardService {

    @GET("Card")
    fun getCards(): Call<ArrayList<CardElement>>

    @POST("Card")
    fun addCard(@Body card: CardElement): Call<CardElement>
}