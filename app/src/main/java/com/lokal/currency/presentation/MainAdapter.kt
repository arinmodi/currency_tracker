package com.lokal.currency.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lokal.currancy.R
import com.lokal.currancy.databinding.CurrencyListItemBinding
import com.lokal.currency.data.model.currancy.Currency
import com.lokal.currency.data.model.currancy.CurrencyList

class MainAdapter(
    private val context: Context,
    private val currencyList: CurrencyList
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(
        private val binding: CurrencyListItemBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            currency: Currency
        ) {
            binding.name.text = currency.name
            binding.rate.text = context.getString(R.string.dollar) + " " + String.format("%.6f", currency.rate)
            Glide.with(context)
                .load(currency.icon)
                .placeholder(R.drawable.coin_placeholder)
                .into(binding.icon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CurrencyListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.currency_list_item,
            parent,
            false
        )
        return MainViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(currencyList[position])
    }

}