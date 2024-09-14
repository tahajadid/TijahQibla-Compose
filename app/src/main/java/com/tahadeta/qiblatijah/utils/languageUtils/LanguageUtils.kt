package com.tahadeta.qiblatijah.utils.languageUtils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale


fun updateLanguage(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}