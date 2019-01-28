package com.gilgoldzweig.mvp.preferences

import android.content.SharedPreferences

import java.util.HashMap


/**
 * Mock implementation of shared preference, which just saves data in memory using map.
 */
class MockSharedPreference : SharedPreferences {

	private var preferenceMap = HashMap<String, Any>()
	private var uncommittedPreferenceMap = HashMap<String, Any>()

	private val preferenceEditor: MockSharedPreferenceEditor =
			MockSharedPreferenceEditor(preferenceMap, uncommittedPreferenceMap)


	override fun getAll(): Map<String, *> = preferenceMap

	override fun getString(key: String, defaultValue: String): String? =
			preferenceMap.getOrDefault(key, defaultValue) as String?

	override fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? =
			preferenceMap.getOrDefault(key, defaultValue) as Set<String>?

	override fun getInt(key: String, defaultValue: Int): Int =
			preferenceMap.getOrDefault(key, defaultValue) as Int

	override fun getLong(key: String, defaultValue: Long): Long =
			preferenceMap.getOrDefault(key, defaultValue) as Long

	override fun getFloat(key: String, defaultValue: Float): Float =
			preferenceMap.getOrDefault(key, defaultValue) as Float

	override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
			preferenceMap.getOrDefault(key, defaultValue) as Boolean

	override fun contains(key: String): Boolean =
			key in preferenceMap

	fun uncommittedContains(key: String): Boolean =
			key in uncommittedPreferenceMap

	override fun edit(): SharedPreferences.Editor =
			preferenceEditor

	override fun registerOnSharedPreferenceChangeListener(
			onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {

	}

	override fun unregisterOnSharedPreferenceChangeListener(
			onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {

	}

	class MockSharedPreferenceEditor(private val preferenceMap: MutableMap<String, Any>,
	                                 private val uncommittedPreferenceMap: MutableMap<String, Any>) :
			SharedPreferences.Editor {

		private var uncommitteRemoveKeys = ArrayList<String>()

		override fun putString(key: String, value: String): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun putStringSet(key: String, value: Set<String>): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun putInt(key: String, value: Int): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun putLong(key: String, value: Long): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		override fun remove(key: String): SharedPreferences.Editor {
			uncommittedPreferenceMap.remove(key)
			uncommitteRemoveKeys.add(key)
			return this
		}

		override fun clear(): SharedPreferences.Editor {
			uncommitteRemoveKeys.clear()
			preferenceMap.clear()
			uncommittedPreferenceMap.clear()
			return this
		}

		override fun commit(): Boolean {
			uncommitteRemoveKeys.forEach {
				preferenceMap.remove(it)
			}

			uncommittedPreferenceMap.forEach {
				preferenceMap[it.key] = it.value
			}

			uncommitteRemoveKeys.clear()
			uncommittedPreferenceMap.clear()
			return true
		}

		fun commitFailure(): Boolean {
			return false
		}


		override fun apply() {
			commit()
		}


	}

}