package com.slama.themoviedb.main.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.R

class MainMovieViewHolder(
    itemView: View,
    onItemClickListener: (Int) -> Unit,
    private val baseImageUrl: String
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            onItemClickListener(adapterPosition)
        }
    }

    private val movieImageView = itemView.findViewById<ImageView>(R.id.item_movie_image)
    private val titleView = itemView.findViewById<TextView>(R.id.item_movie_title)
    private val movieOverview = itemView.findViewById<TextView>(R.id.item_movie_overview)
    private val releaseDateView = itemView.findViewById<TextView>(R.id.item_movie_release_date)

    fun bind(movieOverview: MovieOverview) {
        bindImage(movieOverview.backgroundImagePath)
        bindTitle(movieOverview.title)
        bindOverview(movieOverview.overview)
        bindReleaseDate(movieOverview.releaseDate)
    }

    private fun bindReleaseDate(releaseDate: String) {
        releaseDateView.text = releaseDate
    }

    private fun bindOverview(overview: String) {
        movieOverview.text = overview
    }

    private fun bindTitle(title: String) {
        titleView.text = title
    }

    private fun bindImage(backgroundImagePath: String) {
        val imageUri = buildImageUrl(backgroundImagePath)
        Glide
            .with(itemView.context)
            .load(imageUri)
            .into(movieImageView)
    }

    private fun buildImageUrl(backgroundImagePath: String): Uri {
        return Uri
            .parse(baseImageUrl)
            .buildUpon()
            .appendPath(backgroundImagePath)
            .build()
    }
}