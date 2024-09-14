package com.tahadeta.qiblatijah.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class PreferencesDataStore (val context: Context) {

    companion object {
        val WIDGET_KEY = stringPreferencesKey("selected_widget")
        val LANG_SELECTED = stringPreferencesKey("selected_widget")
    }

    val getWidgetName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[WIDGET_KEY]
        }

    val getLanguageSelected: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LANG_SELECTED]
        }

    suspend fun saveWidgetName(name: String){
        context.dataStore.edit { preferences ->
            preferences[WIDGET_KEY] = name
        }
    }

    suspend fun saveLanguageSelected(lang: String){
        context.dataStore.edit { preferences ->
            preferences[LANG_SELECTED] = lang
        }
    }
}