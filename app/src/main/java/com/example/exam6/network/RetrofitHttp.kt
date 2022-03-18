package com.example.pinterest.network

import com.example.exam6.network.service.CardService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    private const val IS_TESTER = true
    private const val SERVER_DEVELOPMENT = "https://62330a986de3467dbac6b413.mockapi.io/"
    private const val SERVER_PRODUCTION = "https://62330a986de3467dbac6b413.mockapi.io/"

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER)
            return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val photoService: CardService = retrofit.create(CardService::class.java)
}