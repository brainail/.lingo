package org.brainail.EverboxingLingo.data.settings

import android.content.SharedPreferences

internal object PrefDelegates {
    fun stringPref(key: String, defaultValue: String?): kotlin.properties.ReadWriteProperty<SharedPreferences, String?>
            = object : kotlin.properties.ReadWriteProperty<SharedPreferences, String?> {

        override fun getValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>): String?
                = thisRef.getString(key, defaultValue)
        override fun setValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>, value: String?)
                = thisRef.edit().putString(key, value).apply()
    }

    fun intPref(key: String, defaultValue: Int): kotlin.properties.ReadWriteProperty<SharedPreferences, Int>
            = object : kotlin.properties.ReadWriteProperty<SharedPreferences, Int> {

        override fun getValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>): Int
                = thisRef.getInt(key, defaultValue)
        override fun setValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>, value: Int)
                = thisRef.edit().putInt(key, value).apply()
    }

    fun booleanPref(key: String, defaultValue: Boolean): kotlin.properties.ReadWriteProperty<SharedPreferences, Boolean>
            = object : kotlin.properties.ReadWriteProperty<SharedPreferences, Boolean> {

        override fun getValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>): Boolean
                = thisRef.getBoolean(key, defaultValue)
        override fun setValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>, value: Boolean)
                = thisRef.edit().putBoolean(key, value).apply()
    }

    fun longPref(key: String, defaultValue: Long): kotlin.properties.ReadWriteProperty<SharedPreferences, Long>
            = object : kotlin.properties.ReadWriteProperty<SharedPreferences, Long> {

        override fun getValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>): Long
                = thisRef.getLong(key, defaultValue)
        override fun setValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>, value: Long)
                = thisRef.edit().putLong(key, value).apply()
    }

    fun floatPref(key: String, defaultValue: Float): kotlin.properties.ReadWriteProperty<SharedPreferences, Float>
            = object : kotlin.properties.ReadWriteProperty<SharedPreferences, Float> {

        override fun getValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>): Float
                = thisRef.getFloat(key, defaultValue)
        override fun setValue(thisRef: android.content.SharedPreferences, property: kotlin.reflect.KProperty<*>, value: Float)
                = thisRef.edit().putFloat(key, value).apply()
    }
}

