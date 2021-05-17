 package com.picpay.contact

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.picpay.contact.databinding.ActivityContactBinding
import dagger.hilt.android.AndroidEntryPoint

 /**
 * Main activity of the contact module. This activity will be responsible to handle
 * all module navigation.
 */
 @AndroidEntryPoint class ContactActivity : AppCompatActivity() {
     private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@ContactActivity, R.layout.activity_contact)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}