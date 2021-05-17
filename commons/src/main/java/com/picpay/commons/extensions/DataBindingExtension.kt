package com.picpay.commons.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun <T : ViewDataBinding> AppCompatActivity.bindingContentView(layout: Int): T =
    DataBindingUtil.setContentView(this, layout) as T

fun <T : ViewDataBinding> Fragment.bindingContentView(layout: Int, parent: ViewGroup?): T =
    DataBindingUtil.inflate(
        LayoutInflater.from(this.context),
        layout,
        parent,
        false
    ) as T
