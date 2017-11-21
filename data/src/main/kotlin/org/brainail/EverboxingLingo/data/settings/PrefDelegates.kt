package org.brainail.EverboxingLingo.data.settings

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal object PrefDelegates {
    fun stringPref(key: String, defaultValue: String?): ReadWriteProperty<SharedPreferences, String?>
            = object : ReadWriteProperty<SharedPreferences, String?> {

        override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): String?
                = thisRef.getString(key, defaultValue)

        override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: String?)
                = thisRef.edit().putString(key, value).apply()
    }

    fun intPref(key: String, defaultValue: Int): ReadWriteProperty<SharedPreferences, Int>
            = object : ReadWriteProperty<SharedPreferences, Int> {

        override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): Int
                = thisRef.getInt(key, defaultValue)

        override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: Int)
                = thisRef.edit().putInt(key, value).apply()
    }

    fun booleanPref(key: String, defaultValue: Boolean): ReadWriteProperty<SharedPreferences, Boolean>
            = object : ReadWriteProperty<SharedPreferences, Boolean> {

        override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): Boolean
                = thisRef.getBoolean(key, defaultValue)

        override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: Boolean)
                = thisRef.edit().putBoolean(key, value).apply()
    }

    fun longPref(key: String, defaultValue: Long): ReadWriteProperty<SharedPreferences, Long>
            = object : ReadWriteProperty<SharedPreferences, Long> {

        override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): Long
                = thisRef.getLong(key, defaultValue)

        override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: Long)
                = thisRef.edit().putLong(key, value).apply()
    }

    fun floatPref(key: String, defaultValue: Float): ReadWriteProperty<SharedPreferences, Float>
            = object : ReadWriteProperty<SharedPreferences, Float> {

        override fun getValue(thisRef: SharedPreferences, property: KProperty<*>): Float
                = thisRef.getFloat(key, defaultValue)

        override fun setValue(thisRef: SharedPreferences, property: KProperty<*>, value: Float)
                = thisRef.edit().putFloat(key, value).apply()
    }
}

