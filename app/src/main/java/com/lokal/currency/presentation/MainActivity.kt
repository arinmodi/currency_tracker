package com.lokal.currency.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lokal.currancy.R
import com.lokal.currancy.databinding.ActivityMainBinding
import com.lokal.currency.data.model.currancy.CurrencyList
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel

    private var mainAdapter: MainAdapter? = null

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis: Long = 180000

    private val runnable = object : Runnable {
        override fun run() {
            // Call your function here
            getCurrencyData()

            // Schedule the next execution
            handler.postDelayed(this, delayMillis)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainViewModel =
            ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        getCurrencyData()
        setObservers()
        setSwipeRefreshLayout()
        setClickListener()
        handler.post(runnable)
    }

    private fun setClickListener() {
        binding.tryAgain.setOnClickListener {
            getCurrencyData()
        }
    }

    private fun setAdapter(data : CurrencyList) {
        binding.rvCoins.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvCoins.setHasFixedSize(true)
        mainAdapter = MainAdapter(
            this,
            data
        )
        binding.rvCoins.adapter = mainAdapter
    }


    private fun getCurrencyData() {
        binding.pgLoading.visibility = View.VISIBLE
        mainViewModel.getCryptoList()
    }

    private fun setObservers() {
        mainViewModel.data.observe(this) {
            binding.pgLoading.visibility = View.GONE
            setAdapter(it)
            setRefreshTime()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.errorLayout.visibility = View.GONE
        }

        mainViewModel.dataFailure.observe(this) {
            binding.pgLoading.visibility = View.GONE
            binding.tvRefreshTime.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
            binding.errorLayout.visibility = View.VISIBLE
        }

    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setRefreshTime() {
        binding.tvRefreshTime.visibility=View.VISIBLE
        val dateFormat = SimpleDateFormat("HH:mm")
        val currentTime = Date()
        binding.tvRefreshTime.text = getString(R.string.last_refreshed) + " " + dateFormat.format(currentTime)
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing=true
            getCurrencyData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

}