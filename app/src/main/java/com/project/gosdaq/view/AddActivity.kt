package com.project.gosdaq.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.project.gosdaq.contract.AddContract
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.databinding.ActivityAddBinding
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity(), AddContract.AddView {

    private lateinit var binding: ActivityAddBinding

    private val gosdaqRepository: GosdaqRepository by lazy {
        GosdaqRepository.getInstance(this)
    }
    private val addPresenter: AddPresenter by lazy {
        AddPresenter(gosdaqRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(100)
            binding.addTitle.visibility = View.VISIBLE
            delay(200)
            binding.textView.visibility = View.VISIBLE
            delay(300)
            binding.tickerCountryRadioGroup.visibility = View.VISIBLE
        }

        binding.krRadioButton.setOnClickListener {
            binding.tickerArea.visibility = View.VISIBLE
        }

        binding.usRadioButton.setOnClickListener {
            binding.tickerArea.visibility = View.VISIBLE
        }

        binding.addTickerButton.setOnClickListener {
            val regionRadioButtonStatus = when (binding.usRadioButton.isChecked) {
                true -> Region.US()
                else -> Region.KR()
            }
            val inputTicker = this.binding.tickerEditText.text.toString()

            addPresenter.insertInterestingData(lifecycleScope, inputTicker, regionRadioButtonStatus)
        }
    }
}