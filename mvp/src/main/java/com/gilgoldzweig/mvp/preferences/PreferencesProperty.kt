package com.gilgoldzweig.mvp.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegation for [GlobalSharedPreferences]
 */
class PreferencesProperty<T : Any> internal constructor(
    private val key: String = "",
    private val defaultValue: T,
    private val background: Boolean = false,
    private val localValue: Boolean = false
) : ReadWriteProperty<Any, T> {

    private val pref = GlobalSharedPreferences


    private var lastCommitedValue: T? = null

    /**
     * Every time we call set on our delegated property
     */
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val prefsKey = if (key.isEmpty()) property.name else key

        pref.Editor.set(prefsKey, value)

        if (background) {
            GlobalSharedPreferences.Editor.apply()
        } else {
            GlobalSharedPreferences.Editor.commit()
        }
    }

    /**
     * Every time we call get on our delegated property
     */
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.get(if (key.isEmpty()) property.name else key, defaultValue)

}

/**
 * Extension function for PreferencesProperty
 */
fun <T : Any> preferences(
    key: String = "",
    defaultValue: T,
    background: Boolean = false
): PreferencesProperty<T> =
    PreferencesProperty(key, defaultValue, background)
