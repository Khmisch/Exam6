package com.example.exam6.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam6.R
import com.example.exam6.adapter.PhysicalAdapter
import com.example.exam6.model.Card
import com.example.exam6.model.CardElement
import com.example.exam6.model.CardResp
import com.example.pinterest.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhysicalFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PhysicalAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_physical, container, false)

        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(context,1))
        progressBar = view.findViewById(R.id.progress_circular)
        apiGetCard()
    }

    private fun apiGetCard() {
        progressBar.visibility = View.VISIBLE
        RetrofitHttp.photoService.getCards()
            .enqueue(object : Callback<ArrayList<CardElement>> {
                override fun onResponse(call: Call<ArrayList<CardElement>>, response: Response<ArrayList<CardElement>>) {
                    progressBar.visibility = View.GONE

                    var body = response.body()
                    if (body != null) {
                        Log.d("@@@onSuccessGet",response.body()!!.toString() )
                        refreshAdapter(body)
                    }
                }

                override fun onFailure(call: Call<ArrayList<CardElement>>, t: Throwable) {
                    Log.e("@@@onFailureGet", t.message.toString())
                    Log.e("@@@onFailureGet", t.toString())
                }
            })
    }

    private fun refreshAdapter(card:ArrayList<CardElement>) {
        adapter = PhysicalAdapter(this,card)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}