package ru.hzkrym.cocktailapp

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("actionClick")
fun View.actionClick(action: () -> Unit) {
    setOnClickListener {
        action()
    }
}