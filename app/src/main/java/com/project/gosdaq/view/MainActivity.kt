package com.project.gosdaq.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.gosdaq.adaptor.InterestingAdaptor
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.data.room.InterestingData
import com.project.gosdaq.databinding.ActivityMainBinding
import com.project.gosdaq.repository.GosdaqRepository

class MainActivity : AppCompatActivity(), InterestingContract.InterestingView {

    private lateinit var adapter: InterestingAdaptor

    private val gosdaqRepository: GosdaqRepository by lazy {
        GosdaqRepository.getInstance(this)
    }
    private val mainPresenter: MainPresenter by lazy {
        MainPresenter(this, gosdaqRepository)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setFloatingActionButton()
        mainPresenter.setInterestingDataList(lifecycleScope)
    }


    private fun setFloatingActionButton(){
        binding.addItem.setOnClickListener {
            addInterestingItem()
        }
    }

    override fun setShimmerVisibility(visibility: Boolean) {
        when (visibility) {
            true -> {
                binding.favoriteShimmerLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.favoriteShimmerLayout.startShimmer()
            }
            else -> {
                binding.favoriteShimmerLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.favoriteShimmerLayout.stopShimmer()
            }
        }
    }

    override fun initInterestingRecyclerView() {
        adapter = InterestingAdaptor().apply {
            updateData(InterestingData.interestingTickerList)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun updateInterestingRecyclerView(newData: MutableList<InterestingResponseDataElement>) {
        adapter.updateData(newData)
    }

    fun addInterestingItem(){
        val interestingActivityIntent = Intent(this, AddActivity::class.java)
        startActivity(interestingActivityIntent)
    }

    override fun onRestart() {
        super.onRestart()
        adapter.updateData(InterestingData.interestingTickerList)
    }
}