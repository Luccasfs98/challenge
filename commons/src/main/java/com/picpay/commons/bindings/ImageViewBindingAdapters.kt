package com.picpay.commons.bindings

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.transform.CircleCropTransformation
import com.picpay.commons.R


@BindingAdapter("imageUrl", "placeholderId", requireAll = false)
fun ImageView.setImageUrl (url: String?, placeholderId: Int?) {
    load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholderId?.let {
            placeholder(it)
        }?: run {
            val progress = CircularProgressDrawable(context)
            progress.strokeWidth = 5f
            progress.centerRadius = 30f
            progress.start()
            placeholder(progress)
        }
        error(R.drawable.loading_image_error)
    }

}
