package com.example.exam6.model

import com.google.gson.annotations.SerializedName

data class CardResp (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("cardNumber")
    val cardNumber: String? = null,
    @SerializedName("holderName")
    val holderName: String? = null,
    @SerializedName("expDateMonth")
    val expDateMonth: String? = null,
    @SerializedName("expDateYear")
    val expDateYear: String? = null,
    @SerializedName("cvv")
    val cvv: String? = null,
    @SerializedName("isServer")
    val isServer: Boolean = false
)