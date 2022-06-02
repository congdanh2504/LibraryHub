package com.example.libraryhub.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore("my_preference")

    private object PreferenceKeys {
        val user = preferencesKey<String>("user")
    }

    suspend fun saveUser(user: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.user] = user
        }
    }

    val readUser: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val user = preference[PreferenceKeys.user] ?: ""
            user
        }
}