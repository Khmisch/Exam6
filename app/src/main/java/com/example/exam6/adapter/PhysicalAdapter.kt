package com.example.exam6.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exam6.R
import com.example.exam6.fragment.PhysicalFragment
import com.example.exam6.model.Card
import com.example.exam6.model.CardElement

class PhysicalAdapter (var context: PhysicalFragment,  private var items :ArrayList<CardElement>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    @SuppressLint("NotifyDataSetChanged")
    fun addPhotos(photoList: ArrayList<CardElement>) {
        this.items.addAll(photoList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cards, parent, false)
        return PinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardItem = items[position]
        if (holder is PinsViewHolder) {
            holder.tv_card_number.text = cardItem.card_number
            holder.tv_fullname.text = cardItem.full_name
            holder.tv_expire_date_month.text = cardItem.exp_date_month
            holder.tv_expire_date_year.text = cardItem.exp_date_year
        }
    }

    class PinsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tv_card_number: TextView = view.findViewById(R.id.tv_card_number)
        var tv_fullname: TextView = view.findViewById(R.id.tv_fullname)
        var tv_expire_date_month: TextView = view.findViewById(R.id.tv_expire_date_month)
        var tv_expire_date_year: TextView = view.findViewById(R.id.tv_expire_date_year)
        var tv_balance: TextView = view.findViewById(R.id.tv_balance)
    }

}