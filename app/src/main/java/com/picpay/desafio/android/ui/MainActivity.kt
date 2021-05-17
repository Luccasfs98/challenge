package com.picpay.desafio.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.core.navigation.CrossModuleNavigationHelper.navigateToContact
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToContact(this)?.let {
            startActivity(it)
        }
        finish()
    }
}
