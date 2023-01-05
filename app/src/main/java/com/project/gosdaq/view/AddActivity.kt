package com.project.gosdaq.view

import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.setPadding
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
        AddPresenter(this, gosdaqRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showElement()
        initListener()
    }

    private fun initListener() {
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

            val keyboard = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            binding.progressArea.visibility = View.VISIBLE

            addPresenter.insertInterestingData(lifecycleScope, inputTicker, regionRadioButtonStatus)
        }
    }

    override fun onAddTickerSuccess() {
        binding.find.visibility = View.GONE
        binding.allFind.visibility = View.VISIBLE
        finish()
    }

    override fun onAddTickerFailure(isDuplicated: Boolean, ticker: String) {
        when (isDuplicated) {
            true -> {
                binding.progressArea.visibility = View.GONE
                binding.find.visibility = View.VISIBLE
                binding.allFind.visibility = View.GONE
                Toast.makeText(this@AddActivity, "${ticker}는 이미 추가되어 있는 종목이에요.", Toast.LENGTH_SHORT).show()
            }
            false -> {
                binding.progressArea.visibility = View.GONE
                binding.find.visibility = View.VISIBLE
                binding.allFind.visibility = View.GONE
                Toast.makeText(this@AddActivity, "${ticker}는 현재 추가 할 수 없는 종목이에요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showElement() {
        lifecycleScope.launch {
            delay(100)
            binding.addTitle.visibility = View.VISIBLE
            delay(200)
            binding.textView.visibility = View.VISIBLE
            delay(300)
            binding.tickerCountryRadioGroup.visibility = View.VISIBLE
        }
    }
}