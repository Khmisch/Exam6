package com.example.pinterest.database

import android.app.Application
import com.example.exam6.manager.RoomManager
import com.example.exam6.model.CardElement
import com.example.exam6.model.Cards

class PinRepository(application: Application) {

    private val dp = RoomManager.getInstance(application)
    private var pinDao = dp.pinDao()

    fun savePhoto(pin: Cards) {
        pinDao.savePhoto(pin)
    }

    fun getAllSavedPhotos(): List<Cards> {
        return pinDao.getAllSavedPhotos()
    }

    fun clearSavedPhotos() {
        pinDao.clearSavedPhotos()
    }

    fun removeSavedPhotos(id: Int) {
        pinDao.removeSavedPhotos(id)
    }

}