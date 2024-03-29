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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddActivity: AppCompatActivity(), AddContract.AddView {

    @Inject
    lateinit var addPresenterFactory: AddPresenter.AddPresenterFactory

    private val addPresenter: AddPresenter by lazy{
        addPresenterFactory.create(this)
    }

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showElement()
        initListener()
    }

    private fun initListener() {
        binding.radioKr.setOnClickListener {
            binding.tickerArea.visibility = View.VISIBLE
        }

        binding.radioUs.setOnClickListener {
            binding.tickerArea.visibility = View.VISIBLE
        }

        binding.addTickerButton.setOnClickListener {
            val regionRadioButtonStatus = when (binding.radioUs.isChecked) {
                true -> Region.US()
                else -> Region.KR()
            }
            val inputTicker = this.binding.tickerEditText.text.toString()

            val keyboard = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            binding.layoutTickerProgress.visibility = View.VISIBLE

            addPresenter.insertInterestingData(lifecycleScope, inputTicker, regionRadioButtonStatus)
        }
    }

    override fun onAddTickerSuccess() {
        binding.textFindTickerEnd.visibility = View.VISIBLE
        binding.textFindTickerStart.visibility = View.GONE
        finish()
    }

    override fun onAddTickerFailure(isDuplicated: Boolean, ticker: String) {
        when (isDuplicated) {
            true -> {
                binding.layoutTickerProgress.visibility = View.GONE
                binding.textFindTickerStart.visibility = View.VISIBLE
                binding.textFindTickerEnd.visibility = View.GONE
                Toast.makeText(this@AddActivity, "${ticker}는 이미 추가되어 있는 종목이에요.", Toast.LENGTH_SHORT).show()
            }
            false -> {
                binding.layoutTickerProgress.visibility = View.GONE
                binding.textFindTickerStart.visibility = View.VISIBLE
                binding.textFindTickerEnd.visibility = View.GONE
                Toast.makeText(this@AddActivity, "${ticker}는 현재 추가 할 수 없는 종목이에요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showElement() {
        lifecycleScope.launch {
            delay(100)
            binding.textAddTitle.visibility = View.VISIBLE
            delay(200)
            binding.textAddDescription.visibility = View.VISIBLE
            delay(300)
            binding.radioGroupCountry.visibility = View.VISIBLE
        }
    }
}