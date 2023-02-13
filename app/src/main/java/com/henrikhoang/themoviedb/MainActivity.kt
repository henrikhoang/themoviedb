package com.henrikhoang.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import com.henrikhoang.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.Query

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

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
        binding.svMovieQuery.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) return false
                viewModel.updateQuery(query, false)
                return true
            }
        })
    }


}