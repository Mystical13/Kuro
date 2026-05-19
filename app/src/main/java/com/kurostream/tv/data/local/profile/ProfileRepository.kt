package com.kurostream.tv.data.local.profile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileStore: DataStore<Preferences> by preferencesDataStore(name = "profiles")

@Singleton
class ProfileRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun createProfile(name: String, pin: String) = withContext(Dispatchers.IO.limitedParallelism(2)) {
        val hashedPin = hashPin(pin)
        val key = stringPreferencesKey("profile_$name")
        context.profileStore.edit { prefs ->
            prefs[key] = hashedPin
            // TODO: PHASE_3 Init profile-scoped Room DB tables
        }
    }

    fun verifyPin(name: String, pin: String): Flow<Boolean> {
        val key = stringPreferencesKey("profile_$name")
        val hashedPin = hashPin(pin)
        return context.profileStore.data.map { prefs ->
            prefs[key] == hashedPin
        }
    }

    private fun hashPin(pin: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(pin.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
