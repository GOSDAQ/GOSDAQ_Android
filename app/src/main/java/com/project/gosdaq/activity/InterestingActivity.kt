package com.project.gosdaq.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.gosdaq.databinding.ActivityInterestingBinding
import com.project.gosdaq.databinding.ActivityMainBinding

class InterestingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterestingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}