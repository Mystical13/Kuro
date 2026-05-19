package com.kurostream.tv.data.remote.anilist

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "anilist_prefs")

@Singleton
class AniListSyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val tokenKey = stringPreferencesKey("anilist_token")

    suspend fun getAuthUrl(clientId: String): String {
        return "https://anilist.co/api/v2/oauth/authorize?client_id=$clientId&response_type=token"
    }

    suspend fun saveToken(token: String) = withContext(Dispatchers.IO.limitedParallelism(2)) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token // TODO: PHASE_3 Use EncryptedSharedPreferences/Keystore
        }
    }
    
    suspend fun getToken(): String? = withContext(Dispatchers.IO.limitedParallelism(2)) {
        context.dataStore.data.map { prefs -> prefs[tokenKey] }.first()
    }

    suspend fun autoScrobble(mediaId: Int, progress: Int, totalEpisodes: Int) = withContext(Dispatchers.IO.limitedParallelism(2)) {
        if (totalEpisodes > 0 && (progress.toFloat() / totalEpisodes) >= 0.8f) {
            // TODO: PHASE_3 Execute GraphQL mutation for watchlist & progress
        }
    }
}
