package com.picpay.core.extensions
import android.content.Intent

inline fun <reified T : Enum<T>> Intent.putEnumExtra(key: String, enum: T) =
    putExtra(key, enum.ordinal)

inline fun <reified T: Enum<T>> Intent.getEnumExtra(key: String): T? =
    getIntExtra(key, -1)
        .takeUnless { it == -1 }
        ?.let { T::class.java.enumConstants?.get(it) }