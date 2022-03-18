package com.example.exam6.activity

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.exam6.R
import com.example.exam6.adapter.PhysicalAdapter
import com.example.exam6.database.CardRepository
import com.example.exam6.model.Cards
import com.example.pinterest.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCardActivity : AppCompatActivity() {

    lateinit var iv_close: ImageView
    lateinit var tv_card_number: TextView
    lateinit var tv_fullname: TextView
    lateinit var tv_slash: TextView
    lateinit var tv_expire_date_month: TextView
    lateinit var tv_expire_date_year: TextView
    lateinit var et_card_number: EditText
    lateinit var et_date_year: EditText
    lateinit var et_cvv: EditText
    lateinit var et_date_month: EditText
    lateinit var et_fullname: EditText
    lateinit var bt_add_card: Button
    lateinit var adapter: PhysicalAdapter
    var posters = ArrayList<Cards>()
    private lateinit var cardRepository: CardRepository
    private val nonDigits = Regex("[^\\d]")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        cardRepository = CardRepository(application)
        initViews()
    }

    private fun initViews() {
        iv_close = findViewById(R.id.iv_close)
        bt_add_card = findViewById(R.id.bt_add_card)
        tv_card_number = findViewById(R.id.tv_card_number)
        tv_fullname = findViewById(R.id.tv_fullname)
        tv_slash = findViewById(R.id.tv_slash)
        tv_expire_date_month = findViewById(R.id.tv_expire_date_month)
        tv_expire_date_year = findViewById(R.id.tv_expire_date_year)
        et_card_number = findViewById(R.id.et_card_number)
        et_cvv = findViewById(R.id.et_cvv)
        et_date_year = findViewById(R.id.et_date_year)
        et_date_month = findViewById(R.id.et_date_month)
        et_fullname = findViewById(R.id.et_fullname)
        iv_close.setOnClickListener {
            finish()
        }


        textWatcher()

        bt_add_card.setOnClickListener {
            if (et_fullname.text.toString().isEmpty()
                ||et_date_month.text.toString().isEmpty()
                ||et_card_number.text.toString().isEmpty()
                ||et_cvv.text.toString().isEmpty()
                ||et_date_year.text.toString().isEmpty()){
                bt_add_card.setError("Fill the empty places")
                Toast.makeText(this, "Fill all the blanks", Toast.LENGTH_LONG).show();
            }
            else{
                 if (isInternetAvailable()){
                     val card = Cards(cvv = et_cvv.text.toString() , full_name = et_fullname.text.toString(), exp_date_month = et_date_month.text.toString(),
                         exp_date_year = et_date_year.text.toString(), card_number = et_card_number.text.toString(),true)

                     saveCardToServer(card)
                } else {
                     val card = Cards(cvv = et_cvv.text.toString() , full_name = et_fullname.text.toString(), exp_date_month = et_date_month.text.toString(),
                         exp_date_year = et_date_year.text.toString(), card_number = et_card_number.text.toString(),false)
                     saveCardToLocal(card)}
            }
        }
    }


    private fun backToFinish(){
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveCardToServer(card: Cards) {
        RetrofitHttp.photoService.addCard(card).enqueue(object : Callback<Cards> {
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                saveCardToLocal(card)
                Log.d("Post", response.body().toString())
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Log.d("Post", t.localizedMessage!!)
            }

        })
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

    private fun saveCardToLocal(card: Cards) {
        cardRepository.cardDao!!.saveCard(card)
        backToFinish()
    }

    private fun textWatcher() {
        var current = ""

        et_card_number.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tv_card_number.setText(et_card_number.getText().toString())

            }
            override fun afterTextChanged(s: Editable) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits,"")
                    if (userInput.length <= 16) {
                        current = userInput.chunked(4).joinToString(" ")
                        s.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s.replace(0, s.length, current, 0, current.length)
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
        et_fullname.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tv_fullname.setText(et_fullname.getText().toString())

            }
            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
        et_cvv.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits,"")
                    if (userInput.length <= 3) {
                        current = userInput.chunked(3).joinToString("")
                        s.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s.replace(0, s.length, current, 0, current.length)
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
        et_date_month.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tv_expire_date_month.setText(et_date_month.getText().toString())

            }
            override fun afterTextChanged(s: Editable) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits,"")
                    if (userInput.length <= 2) {
                        current = userInput.chunked(2).joinToString("")
                        s.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s.replace(0, s.length, current, 0, current.length)
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
        et_date_year.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tv_expire_date_year.setText(et_date_year.getText().toString())
                tv_slash.visibility = View.VISIBLE
            }
            override fun afterTextChanged(s: Editable) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits,"")
                    if (userInput.length <= 2) {
                        current = userInput.chunked(2).joinToString("")
                        s.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s.replace(0, s.length, current, 0, current.length)
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
    }
}