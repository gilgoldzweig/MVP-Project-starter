package com.gilgoldzweig.mvp.preferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegation for [GlobalSharedPreferences]
 */
class PreferencesProperty<T : Any> internal constructor(
	private val defaultValue: T,
	private val key: String = "",
	private val background: Boolean = false
) : ReadWriteProperty<Any, T> {

	private val pref = GlobalSharedPreferences

	/**
	 * Every time we call set on our delegated property
	 */
	override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
		val prefsKey = if (key.isEmpty()) property.name else key

		if (background) {
			pref.apply(prefsKey, value)
		} else {
			pref.commit(prefsKey, value)
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
	defaultValue: T,
	key: String = "",
	background: Boolean = false
): PreferencesProperty<T> =
	PreferencesProperty(defaultValue, key, background)
