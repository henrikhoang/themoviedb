package com.henrikhoang.themoviedb

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.henrikhoang.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val moviesAdapter by lazy {
        MoviesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMovies.adapter = moviesAdapter
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        onViewCreated(binding, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) {
            moviesAdapter.setData(it.movies.orEmpty())
        }
    }

    private fun onViewCreated(binding: ActivityMainBinding, savedInstanceState: Bundle?) {
        binding.rvMovies.addOnScrollListener(
            object: OnVerticalScrollListener() {
                override fun onScrolledToBottom() {
                    viewModel.updateQuery(binding.svMovieQuery.query.toString(), false)
                }

            }
        )
        binding.svMovieQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) return false
                viewModel.updateQuery(query, true)
                return true
            }
        })
    }
}