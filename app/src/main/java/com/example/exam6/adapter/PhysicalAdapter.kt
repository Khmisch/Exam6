package com.example.exam6.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam6.R
import com.example.exam6.model.Cards

class PhysicalAdapter (var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var cardList :ArrayList<Cards> = ArrayList()

    @JvmName("addCards")
    @SuppressLint("NotifyDataSetChanged")
    fun addCards(cardList: ArrayList<Cards>) {
        cardList.clear()
        cardList.addAll(cardList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cards, parent, false)
        return PinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardItem = cardList[position]
        if (holder is PinsViewHolder) {
            @SuppressLint("NotifyDataSetChanged")
            holder.tv_card_number.text = cardItem.card_number
            holder.tv_fullname.text = cardItem.full_name
            holder.tv_expire_date_month.text = cardItem.exp_date_month
            holder.tv_expire_date_year.text = cardItem.exp_date_year
            holder.iv_visa_logo.setOnClickListener {

            }

        }
    }

    class PinsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tv_card_number: TextView = view.findViewById(R.id.tv_card_number)
        var tv_fullname: TextView = view.findViewById(R.id.tv_fullname)
        var tv_expire_date_month: TextView = view.findViewById(R.id.tv_expire_date_month)
        var tv_expire_date_year: TextView = view.findViewById(R.id.tv_expire_date_year)
        var tv_balance: TextView = view.findViewById(R.id.tv_balance)
        var iv_visa_logo: ImageView = view.findViewById(R.id.iv_visa_logo)
    }

}