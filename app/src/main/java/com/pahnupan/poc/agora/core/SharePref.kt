package com.pahnupan.poc.agora.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class SharePref @Inject constructor(
    @ApplicationContext context: Context
) {

    private val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_UID = "key_uid"
        private const val KEY_ROLE = "key_role"
    }

    var token: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) {
            prefs.edit { putString(KEY_TOKEN, value) }
        }

    var role: String?
        get() = prefs.getString(KEY_ROLE, null)
        set(value) {
            prefs.edit { putString(KEY_ROLE, value) }
        }

    var uid: Int?
        get() = prefs.getInt(KEY_UID, 0)
        set(value) {
            prefs.edit { putInt(KEY_UID, value ?: 0) }
        }

    fun clearToken() {
        prefs.edit {
            remove(KEY_TOKEN)
            remove(KEY_UID)
            remove(KEY_ROLE)

        }
    }
}