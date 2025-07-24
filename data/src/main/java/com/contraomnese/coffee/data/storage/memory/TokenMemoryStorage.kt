package com.contraomnese.coffee.data.storage.memory

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.contraomnese.coffee.data.storage.api.TokenStorage
import androidx.core.content.edit
import com.contraomnese.coffee.data.storage.model.TokenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TokenMemoryStorage(
    context: Context,
): TokenStorage {

    private val tokenState: MutableStateFlow<TokenEntity?> = MutableStateFlow(null)

    companion object {
        private const val PREF_NAME = "secure_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_EXPIRY = "token_expiry"
    }

    private val sharedPrefs = EncryptedSharedPreferences.create(
        context,
        PREF_NAME,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(token: TokenEntity) {
        sharedPrefs.edit {
            putString(KEY_TOKEN, token.token)
            putLong(KEY_EXPIRY, System.currentTimeMillis() + token.expiryMillis)
        }
        tokenState.value = getToken()
    }

    override fun getToken(): TokenEntity? {
        val token = sharedPrefs.getString(KEY_TOKEN, null)
        return token?.let {
            TokenEntity(token = it, expiryMillis = sharedPrefs.getLong(KEY_EXPIRY, 0L))
        }
    }

    override fun observeToken(): Flow<TokenEntity?> = tokenState.asStateFlow()

    override fun clear() {
        sharedPrefs.edit { clear() }
    }
}