package com.example.exam6.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam6.R
import com.example.exam6.activity.AddCardActivity
import com.example.exam6.adapter.PhysicalAdapter
import com.example.exam6.database.CardRepository
import com.example.exam6.model.Cards
import com.example.pinterest.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhysicalFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PhysicalAdapter
    lateinit var progressBar: ProgressBar
    lateinit var iv_add: ImageView
    private lateinit var cardRepository: CardRepository


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_physical, container, false)

        initViews(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
//            apiGetCard()
//            addServer()
//        } else
//            adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
    }

    fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        iv_add = view.findViewById(R.id.iv_add)
        cardRepository = CardRepository(requireActivity().application)
        recyclerView.setLayoutManager(GridLayoutManager(context,1))
        progressBar = view.findViewById(R.id.progress_circular)

        if (verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
            apiGetCard()
            addServer()
        } else
            adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))

        apiGetCard()
        adapter = PhysicalAdapter(requireContext())
        recyclerView.adapter = adapter

        iv_add = view.findViewById(R.id.iv_add)
        iv_add.setOnClickListener {
            callAddCardActivity()
        }

    }

    private fun apiGetCard() {
        progressBar.visibility = View.VISIBLE
        RetrofitHttp.photoService.getCards()
            .enqueue(object : Callback<ArrayList<Cards>> {
                override fun onResponse(call: Call<ArrayList<Cards>>, response: Response<ArrayList<Cards>>) {
                    progressBar.visibility = View.GONE
                    var body = response.body()
                    if (body != null) {
                        adapter.addCards(body)
                        Log.d("@@@onSuccessGet",response.body()!!.toString() )
                    }
                }
                override fun onFailure(call: Call<ArrayList<Cards>>, t: Throwable) {
                    Log.e("@@@onFailureGet", t.message.toString())
                    progressBar.visibility = View.GONE
                    adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
                }
            })
    }

    private fun apiPostCard(card : Cards) {
        progressBar.visibility = View.VISIBLE
        RetrofitHttp.photoService.addCard(card)
            .enqueue(object : Callback<Cards> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                    var body = response.body()
                    if (body != null){
                        progressBar.visibility = View.GONE
                        cardRepository.saveCard(card)
                        adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
                        Log.d("@@@onSuccessPost",response.body()!!.toString() )
                    }
                }

                override fun onFailure(call: Call<Cards>, t: Throwable) {
                    Log.e("@@@onFailurePost", t.message.toString())
                    progressBar.visibility = View.GONE
                    cardRepository.saveCard(card)
                    card.id?.let { cardRepository.changeIsAdded(it, true) }
                    adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
                }
            })
    }


    private fun addServer() {
        val cardList = cardRepository.getAllSavedCard()
        if (cardList.isNotEmpty())
            cardList.forEach {
                if (it.is_server == true) {
                    apiPostCard(it)
                    it.id?.let { it1 -> cardRepository.changeIsAdded(it1, false) }
                }
            }
        else {
            apiGetCard()
        }
    }

    private fun callAddCardActivity() {
        var intent = Intent(requireContext(), AddCardActivity::class.java)
        launcher.launch(intent)
    }
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val card: Cards = Gson().fromJson(it.data!!.getStringExtra("newCard"), Cards::class.java)

            apiPostCard(card)

            cardRepository.saveCard(card)
            adapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
        }
    }
}