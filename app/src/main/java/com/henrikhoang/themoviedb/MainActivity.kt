package com.henrikhoang.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.henrikhoang.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onViewCreated(binding, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {

    }

    private fun onViewCreated(binding: ActivityMainBinding, savedInstanceState: Bundle?) {

    }


}