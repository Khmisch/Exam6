package com.example.exam6.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam6.R
import com.example.exam6.adapter.PhysicalAdapter
import com.example.exam6.database.CardRepository
import com.example.exam6.model.Cards
import com.example.pinterest.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PhysicalAdapter
    lateinit var progressBar: ProgressBar
    lateinit var iv_add: ImageView
    private lateinit var cardRepository: CardRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }


    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        iv_add = findViewById(R.id.iv_add)
        cardRepository = CardRepository(application)
        recyclerView.setLayoutManager(GridLayoutManager(this,1))
        progressBar = findViewById(R.id.progress_circular)
        adapter = PhysicalAdapter(this)
        recyclerView.adapter = adapter

        iv_add = findViewById(R.id.iv_add)
        iv_add.setOnClickListener {
            callAddCardActivity()
        }

        loadCardsFromDatabase()
        offlineCardsFromDatabase()

    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

    private fun callAddCardActivity() {
        val intent = Intent(this, AddCardActivity::class.java)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            loadCardsFromDatabase()

            Log.d("onFinish", " Done ${cardRepository.cardDao!!.getCards().toString()}")


        } else
            Log.d("onFinish", "$requestCode $requestCode")
    }


    private fun offlineCardsFromDatabase() {
        val data = cardRepository.cardDao!!.getOfflineCards()

        if (isInternetAvailable()&& data.isNotEmpty()){

            var index = 0
            saveCardToServer(index, data)

        }
        Log.d("getOfflineCards", data.toString())
    }

    private fun saveCardToServer(index: Int, cards: List<Cards>) {
        var i = index
        var card = cards[index]
        card.is_server = true
        RetrofitHttp.photoService.addCard(card).enqueue(object : Callback<Cards> {
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                Log.d("Post", response.body().toString())

                cardRepository.cardDao!!.update(card)
                i++
                if (response.isSuccessful && i < cards.size-1){
                    saveCardToServer(i, cards)
                }

            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Log.d("Post", t.localizedMessage!!)
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadCardsFromDatabase() {
        adapter.cardList.clear()
        adapter.cardList.addAll(cardRepository.cardDao!!.getCards())
        adapter.notifyDataSetChanged()
//        adapter.addCards(cardRepository.cardDao!!.getCards() as ArrayList<Cards>)
    }
}