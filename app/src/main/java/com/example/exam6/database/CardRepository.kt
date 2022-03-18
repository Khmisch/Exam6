package com.example.exam6.database

import android.app.Application
import com.example.exam6.manager.RoomManager

class CardRepository(application: Application) {

    var cardDao: Dao? = null

    init {
        val db = RoomManager.getInstance(application)
        this.cardDao = db.cardDao()
    }

}