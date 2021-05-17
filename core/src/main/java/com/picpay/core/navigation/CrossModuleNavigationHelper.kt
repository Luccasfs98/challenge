package com.picpay.core.navigation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.picpay.core.extensions.putEnumExtra
import com.picpay.core.navigation.feature.ContactModuleConstants


object CrossModuleNavigationHelper {

    @JvmStatic
    fun navigateToContact(context: Context) =
        Intent(ContactModuleConstants.MODULE_ACTION).takeIf { intent ->
            canNavigate(context, intent)
        }?.also { intent ->
            intent.putEnumExtra(
                DefaultConstants.NAVIGATION_FEATURE_KEY,
                ContactModuleConstants.FeaturesEnum.CONTACT
            )
        }

    private fun canNavigate(context: Context, intent: Intent) =
        context.packageManager
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            .isNotEmpty()
}