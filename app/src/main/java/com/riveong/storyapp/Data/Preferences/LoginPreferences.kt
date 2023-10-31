package com.riveong.storyapp.Data.Preferences
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val LOGGINED = booleanPreferencesKey("Loginned_setting")
    private val JWT_TOKEN = stringPreferencesKey("jwt_token")
    private val USERNAME = stringPreferencesKey("user_name")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[LOGGINED] ?: false
        }
    }

    fun getJwtToken(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[JWT_TOKEN] ?: ""
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[USERNAME] ?: ""
        }
    }

    suspend fun saveThemeSetting(isLoggined: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGGINED] = isLoggined
        }
    }

    suspend fun saveUserData(theToken: String, theName: String){
        dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = theToken
            preferences[USERNAME] = theName
        }
    }

    suspend fun deleteAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}