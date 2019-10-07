package com.gilgoldzweig.mvp.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegation for [GlobalSharedPreferences]
 */
class PreferencesProperty<T : Any> internal constructor(
    private val key: String = "",
    private val defaultValue: T,
    private val background: Boolean = false
) : ReadWriteProperty<Any, T> {

    /**
     * Every time we call set on our delegated property
     */
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val prefsKey = if (key.isEmpty()) property.name else key

        with(GlobalSharedPreferences.Editor) {

            set(prefsKey, value)

            if (background) {
                apply()
            } else {
                commit()
            }
        }
    }

    /**
     * Every time we call get on our delegated property
     */
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        GlobalSharedPreferences.get(if (key.isEmpty()) property.name else key, defaultValue)
}

/**
 * Extension function for PreferencesProperty
 */
fun <T : Any> keyPreference(
    key: String = "",
    defaultValue: T,
    background: Boolean = false
): PreferencesProperty<T> =
    PreferencesProperty(key, defaultValue, background)


/**
 *
 */
@Deprecated(
    "Order changed, between key and defaultValue, may cause breaks",
    ReplaceWith("keyPreference", "com.gilgoldzweig.mvp.preferences")
)
fun <T : Any> preferences(
    defaultValue: T,
    key: String = "",
    background: Boolean = false
): PreferencesProperty<T> =
    PreferencesProperty(key, defaultValue, background)
