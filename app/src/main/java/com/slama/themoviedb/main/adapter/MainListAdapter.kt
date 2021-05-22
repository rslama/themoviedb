package com.slama.themoviedb.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.R

class MainListAdapter(private val baseImageUrl: String) : RecyclerView.Adapter<MainMovieViewHolder>() {

    private val items = mutableListOf<MovieOverview>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_overview, parent, false)
        return MainMovieViewHolder(view, baseImageUrl)
    }

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun addItems(items: List<MovieOverview>) {
        this.items.apply {
            clear()
            addAll(items)
            notifyItemRangeChanged(0, items.size)
        }

    }
}