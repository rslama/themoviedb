package com.slama.themoviedb.util

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtil {

    /**
     * Allow load image from network into given ImageView.
     * Also check if context is still validate to prevent Glide crashed on destroyed activity
     */
    fun loadImageIntoView(
        baseImageUrl: String,
        backgroundImagePath: String?,
        imageView: ImageView,
        context: Context
    ) {
        buildImageUrl(baseImageUrl, backgroundImagePath)?.apply {
            if (context.isValidContext()) {
                Glide
                    .with(context)
                    .load(this)
                    .into(imageView)
            }

        }
    }

    private fun buildImageUrl(
        baseImageUrl: String,
        backgroundImagePath: String?
    ): Uri? {
        return if (TextUtils.isEmpty(backgroundImagePath)) {
            null
        } else {
            return Uri
                .parse(baseImageUrl)
                .buildUpon()
                .appendPath(backgroundImagePath)
                .build()
        }

    }

    private fun Context?.isValidContext(): Boolean {
        return when {
            this == null -> {
                false
            }
            this is Activity -> {

                !(this.isDestroyed || this.isFinishing)

            }
            else -> {
                true
            }
        }
    }
}