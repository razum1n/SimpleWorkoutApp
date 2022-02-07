package com.laurirantala.simpleworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laurirantala.simpleworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}