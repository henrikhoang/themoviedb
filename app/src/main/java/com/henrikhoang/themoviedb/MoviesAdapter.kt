package com.henrikhoang.themoviedb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.henrikhoang.themoviedb.MoviesAdapter.MovieViewHolder

class MoviesAdapter(
    private val context: Context,
    var dataSet: List<MovieInfo> = emptyList()
): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.movie_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    fun setData(newData: List<MovieInfo>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val itemView: View): ViewHolder(itemView) {
        val thumbnailIv: ImageView
        val movieNameTv: TextView
        init {
            thumbnailIv = itemView.findViewById(R.id.ivThumbnail)
            movieNameTv = itemView.findViewById(R.id.tvMovieName)
        }
        fun bind(movie: MovieInfo) {
            Glide.with(context).load(movie.poster).into(thumbnailIv)
            movieNameTv.text = movie.title
        }
    }


}