package com.sopt.data.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sopt.data.datasource.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataSource {
    private object PreferencesKeys {
        val accessToken = stringPreferencesKey("accessToken")
        val refreshToken = stringPreferencesKey("refreshToken")
        val userId = intPreferencesKey("userId")
        val isAutoLogin = booleanPreferencesKey("isAutoLogin")
        val nickname = stringPreferencesKey("nickname")
        val profileImage = stringPreferencesKey("profileImage")
    }

    override val accessToken: Flow<String> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.accessToken].orEmpty()
        }

    override val refreshToken: Flow<String> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.refreshToken].orEmpty()
        }

    override val userId: Flow<Int> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.userId] ?: -1
        }

    override val isAutoLogin: Flow<Boolean> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.isAutoLogin] ?: false
        }

    override val nickname: Flow<String> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.nickname].orEmpty()
        }

    override var profileImage: Flow<String> = dataStore.data
        .catch { handleError(it) }
        .map { preferences ->
            preferences[PreferencesKeys.profileImage].orEmpty()
        }

    override suspend fun updateAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.accessToken] = accessToken
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.refreshToken] = refreshToken
        }
    }

    override suspend fun updateUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.userId] = userId
        }
    }

    override suspend fun updateIsAutoLogin(isAutoLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isAutoLogin] = isAutoLogin
        }
    }

    override suspend fun updateNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.nickname] = nickname
        }
    }

    override suspend fun updateProfileImage(profileImage: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.profileImage] = profileImage
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun clearForRefreshToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.accessToken)
            preferences.remove(PreferencesKeys.refreshToken)
            preferences.remove(PreferencesKeys.isAutoLogin)
        }
    }
}

private suspend fun FlowCollector<Preferences>.handleError(it: Throwable) {
    if (it is IOException) {
        emit(emptyPreferences())
    } else {
        throw it
    }
}
