package com.example.exam6.database

import android.app.Application
import com.example.exam6.manager.RoomManager
import com.example.exam6.model.Cards

class CardRepository(application: Application) {

    private val dp = RoomManager.getInstance(application)
    private var cardDao = dp.cardDao()

    fun saveCard(card: Cards) {
        cardDao.saveCard(card)
    }

    fun getAllSavedCard(): List<Cards> {
        return cardDao.getAllSavedCard()
    }

    fun changeIsAdded(id: Int, isAdded: Boolean) {
        cardDao.changeIsAdded(id, isAdded)
    }

}