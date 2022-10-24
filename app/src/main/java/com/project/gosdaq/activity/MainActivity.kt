package com.project.gosdaq.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.project.gosdaq.*
import com.project.gosdaq.databinding.ActivityMainBinding
import com.project.gosdaq.fragment.InterestingFragment
import com.project.gosdaq.fragment.HaveFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val interestingFragment = InterestingFragment()
    private val haveFragment = HaveFragment()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        initFragment()
        setBottomNavigationView()
        setFloatingActionButton()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_layout, interestingFragment, "favorite_fragment")
                .show(interestingFragment)

            add(R.id.frame_layout, haveFragment, "have_fragment")
                .hide(haveFragment)
                .commit()
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragmentManager = supportFragmentManager
            val interestingFragment = fragmentManager.findFragmentByTag("favorite_fragment")
            val haveFragment = fragmentManager.findFragmentByTag("have_fragment")

            when (it.itemId) {
                R.id.favorite -> {
                    fragmentTransaction.hide(haveFragment!!).show(interestingFragment!!).commit()
                    true
                }
                R.id.have -> {
                    fragmentTransaction.hide(interestingFragment!!).show(haveFragment!!).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setFloatingActionButton(){
        binding.addItem.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val interestingFragment = fragmentManager.findFragmentByTag("favorite_fragment")
            val haveFragment = fragmentManager.findFragmentByTag("have_fragment")

            if(interestingFragment!!.isVisible){
                this.interestingFragment.addInterestingItem()
            }else{
                Timber.i("Have Fragment Visible")
            }
        }
    }
}