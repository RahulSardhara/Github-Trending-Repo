package com.graphybyte.githubtrendingrepo.utils

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.graphybyte.githubtrendingrepo.R
import java.util.*

object BindingAdapters {


    @BindingAdapter("setTime")
    @JvmStatic
    fun setTime(textView: TextView?, data: String?) {
        textView?.apply {
            if (data.isNullOrEmpty()) {
                text = ""
                return
            }
            text = String.format(context.getString(R.string.item_date), textView.context.getDate(data), textView.context.getTime(data))
        }
    }

    @BindingAdapter("profilePic")
    @JvmStatic
    fun loadProfilePic(imageView: AppCompatImageView?, url: String?) {
        imageView?.apply {
            if (url.isNullOrEmpty()) {
                setImageResource(R.drawable.ic_placeholder)
                return
            }
            Glide.with(imageView)
                .load(url)
                .error(R.drawable.ic_placeholder)
                .circleCrop()
                .into(this)
        }
    }

    @BindingAdapter("setGradientDrawable")
    @JvmStatic
    fun setGradientDrawable(imageView: AppCompatImageView?, language: String?) {
        imageView?.apply {
            if (language.isNullOrEmpty()) {
                DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, R.color.colorAccent))
                return
            }
            DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, this.context.getColor(language)))
        }
    }

    @BindingAdapter("setAppBarLayoutColor")
    @JvmStatic
    fun setAppBarLayoutColor(appBarLayout: AppBarLayout?, language: String?) {
        appBarLayout?.apply {
            if (language.isNullOrEmpty()) {
                DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, R.color.colorAccent))
                return
            }
            DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, this.context.getColor(language)))
        }
    }

    @BindingAdapter("setButtonColor")
    @JvmStatic
    fun setButtonColor(materialButton: MaterialButton?, language: String?) {
        materialButton?.apply {
            if (language.isNullOrEmpty()) {
                DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, R.color.colorAccent))
                return
            }
            DrawableCompat.setTint(this.background, ContextCompat.getColor(this.context, this.context.getColor(language)))
        }
    }

}