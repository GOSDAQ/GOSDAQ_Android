package com.project.gosdaq.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.project.gosdaq.data.enum.Region
import com.project.gosdaq.databinding.DialogRequestTickerBinding
import com.project.gosdaq.presenter.InterestingPresenter
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddInterestingDialog(presenter: InterestingPresenter) : DialogFragment() {

    private lateinit var binding: DialogRequestTickerBinding
    private val presenter: InterestingPresenter

    init {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DialogRequestTickerBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding.addTickerButton.setOnClickListener {
            val regionRadioButtonStatus = when (binding.usRadioButton.isChecked) {
                true -> Region.US()
                else -> Region.KR()
            }
            val inputTicker = this.binding.tickerEditText.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                presenter.isAvailableTicker(inputTicker, regionRadioButtonStatus)
            }
            this.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        setDialogSize()
    }

    private fun setDialogSize() {
        val height = resources.displayMetrics.heightPixels
        val width = resources.displayMetrics.widthPixels

        val param = dialog?.window?.attributes.apply {
            this?.width = (width * 0.9).toInt()
        } as WindowManager.LayoutParams

        dialog?.window?.attributes = param
    }
}