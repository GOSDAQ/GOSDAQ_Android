package com.project.gosdaq.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.gosdaq.R
import com.project.gosdaq.view.adaptor.InterestingAdaptor
import com.project.gosdaq.view.adaptor.InterestingViewHolder
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.data.interesting.InterestingResponseDataElement
import com.project.gosdaq.data.room.InterestingData
import com.project.gosdaq.databinding.ActivityMainBinding
import com.project.gosdaq.repository.GosdaqRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity(), InterestingContract.InterestingView {

    @Inject
    lateinit var mainPresenterFactory: MainPresenter.MainPresenterFactory

    private val mainPresenter: MainPresenter by lazy{
        mainPresenterFactory.create(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: InterestingAdaptor
    private lateinit var deleteDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSplashScreen()

        setRecycler()
        setPullToRefresh()

        setListener()
        setDialog()
    }

    private fun setSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 360f, 0f)
                val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

                ObjectAnimator.ofPropertyValuesHolder(splashScreenView.iconView, alpha, rotation).run {
                    interpolator = AnticipateInterpolator()
                    duration = 1500L
                    doOnEnd {
                        splashScreenView.remove()
                    }
                    start()
                }
            }
        }
    }

    private fun setRecycler() {
        mainPresenter.setInterestingDataList(lifecycleScope)
    }

    private fun setPullToRefresh() {
        binding.layoutRefresh.setDistanceToTriggerSync(500)

        binding.layoutRefresh.setOnRefreshListener {
            setShimmerVisibility(true)
            mainPresenter.setInterestingDataList(lifecycleScope, true)
            binding.layoutRefresh.isRefreshing = false
        }
    }

    private fun setListener(){
        binding.fabAddTicker.setOnClickListener {
            val interestingActivityIntent = Intent(this, AddActivity::class.java)
            startActivity(interestingActivityIntent)
        }
    }

    private fun setDialog() {
        deleteDialog = MaterialAlertDialogBuilder(this@MainActivity)
    }

    override fun onRestart() {
        super.onRestart()
        adapter.updateData(mainPresenter.interestingData)
    }

    override fun setShimmerVisibility(visibility: Boolean) {
        when (visibility) {
            true -> {
                binding.layoutMainShimmer.visibility = View.VISIBLE
                binding.recyclerTickerList.visibility = View.GONE
                binding.fabAddTicker.visibility = View.GONE
                binding.layoutMainShimmer.startShimmer()
            }
            else -> {
                binding.layoutMainShimmer.visibility = View.GONE
                binding.recyclerTickerList.visibility = View.VISIBLE
                binding.fabAddTicker.visibility = View.VISIBLE
                binding.layoutMainShimmer.stopShimmer()
            }
        }
    }

    override fun initInterestingRecyclerView() {
        var scrollValue = 0

        adapter = InterestingAdaptor().apply {
            setOnItemClickListener(object : InterestingViewHolder.OnItemClickListener {
                override fun onClick(position: Int) {
                    val deleteDialog = createDeleteDialog(position)
                    deleteDialog.show()
                }
            })
            updateData(mainPresenter.interestingData)
        }

        binding.recyclerTickerList.adapter = adapter
        binding.recyclerTickerList.layoutManager = LinearLayoutManager(this)

        binding.recyclerTickerList.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollValue += dy

                if(!binding.recyclerTickerList.canScrollVertically(-1)){
                    binding.fabAddTicker.extend()
                    binding.title.alpha = 0.0F
                    binding.exchange.alpha = 0.0F
                }else{
                    binding.fabAddTicker.shrink()
                    binding.title.alpha = scrollValue/100F
                    binding.exchange.alpha = scrollValue/100F
                }
            }
        })

        binding.fabAddTicker.visibility = View.VISIBLE
    }

    override fun updateInterestingRecyclerView(newData: MutableList<InterestingResponseDataElement>) {
        adapter.updateData(newData)
    }

    override fun initExchange(exchange: String) {
        binding.exchange.text = "${exchange}원"
    }

    override fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createDeleteDialog(pos: Int): AlertDialog {
        val interestingResponseDataElement = mainPresenter.interestingData[pos]
        val deleteDialog = this.deleteDialog
            .setCancelable(false)
            .setTitle("삭제하기")
            .setMessage("${interestingResponseDataElement.name}(${interestingResponseDataElement.ticker})를 삭제할까요?")
            .setPositiveButton("삭제하기") { dialogInterface, i ->
                mainPresenter.deleteTicker(lifecycleScope, pos)
            }
            .setNegativeButton("취소") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()

        deleteDialog.setOnShowListener(object: DialogInterface.OnShowListener{
            override fun onShow(dialogInterface: DialogInterface){
                val btn = deleteDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                // btn.isFocusable = true
                btn.isFocusableInTouchMode = true
                btn.requestFocus()
            }
        })

        return deleteDialog
    }
}