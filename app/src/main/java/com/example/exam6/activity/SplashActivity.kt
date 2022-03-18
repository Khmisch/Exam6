package com.example.exam6.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.exam6.R
import com.example.exam6.database.CardRepository
import com.example.exam6.model.Cards
import com.example.pinterest.network.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initViews()
    }

    private fun initViews(){
        cardRepository = CardRepository(application)
        var size = cardRepository.cardDao!!.getCards().size
        if (size == 0){
            apiLoadCards()
        } else{
            callMainActivity()
        }
    }

    private fun apiLoadCards() {
        RetrofitHttp.photoService.getCards().enqueue(object : Callback<ArrayList<Cards>> {
            override fun onResponse(call: Call<ArrayList<Cards>>, response: Response<ArrayList<Cards>>, ) {
                saveCardsToDatabase(response.body()!!)
                Log.d("@@@OnResponseSplash", response.isSuccessful.toString())
            }

            override fun onFailure(call: Call<ArrayList<Cards>>, t: Throwable) {
                Log.d("@@@OnFailureSplash", t.localizedMessage!!)
            }

        })
    }

    private fun saveCardsToDatabase(items: ArrayList<Cards>) {
        for (item in items){
            cardRepository.cardDao!!.saveCard(item)
        }
        callMainActivity()
    }

    private fun callMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}