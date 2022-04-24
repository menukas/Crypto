package com.amandrykin.crypto.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferencesStorage {
    var timeLoadedAt: Long
}

@Singleton
class SharedPreferencesStorage @Inject constructor(context: Context): PreferencesStorage {

    companion object {
        const val PREFERENCES_NAME = "COINS_PREFS"
        const val PREFERENCES_TIME_LOADED_AT = "PREFS_DATA_LOADED_AT"
    }

    private val preferences: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override var timeLoadedAt by LongPreference(preferences, PREFERENCES_TIME_LOADED_AT, 0)
}

class LongPreference(
    private val preference: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: Long
): ReadWriteProperty<Any, Long> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preference.value.getLong(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preference.value.edit { putLong(name, value) }
    }

}

//    val preferencesStorage = context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
//
//    fun sampleWorkWithPreferences() {
//        preferencesStorage.getBoolean("KEY", false)
//        preferencesStorage.getFloat("KEY", 10F)
//
//        preferencesStorage.edit()
//            .putBoolean("KEY", true)
//            .putFloat("KEY", 10F)
//            .apply()
//    }
