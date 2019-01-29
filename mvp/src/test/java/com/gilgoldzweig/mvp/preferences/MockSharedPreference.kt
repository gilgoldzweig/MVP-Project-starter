package com.gilgoldzweig.mvp.preferences

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import java.util.HashMap
import kotlin.collections.set

/**
 * Mock implementation of [SharedPreferences], which just saves data in memory using map.
 */
@Suppress("UNCHECKED_CAST")
class MockSharedPreference : SharedPreferences {

	/**
	 * Mocks the values that have been committed
	 */
	var preferenceMap = HashMap<String, Any>()

	/**
	 * Mocks the values that have not been committed but have been inserted by the editor
	 */
	var uncommittedPreferenceMap = HashMap<String, Any>()

	/**
	 * Mocked implementation of [SharedPreferences.Editor]
	 */
	private val preferenceEditor: MockSharedPreferenceEditor =
			MockSharedPreferenceEditor(preferenceMap, uncommittedPreferenceMap)

	/**
	 * Mocked the listeners edited by
	 * [registerOnSharedPreferenceChangeListener], [unregisterOnSharedPreferenceChangeListener]
	 */
	private var listeners: MutableSet<OnSharedPreferenceChangeListener> = HashSet()

	/**
	 * Mocked version of [SharedPreferences.getAll]
	 */
	override fun getAll(): Map<String, *> = preferenceMap

	/**
	 * Mocked version of [SharedPreferences.getString]
	 */
	override fun getString(key: String, defaultValue: String): String? =
		preferenceMap.getOrDefault(key, defaultValue) as String?

	/**
	 * Mocked version of [SharedPreferences.getStringSet]
	 */
	override fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? =
		preferenceMap.getOrDefault(key, defaultValue) as Set<String>?

	/**
	 * Mocked version of [SharedPreferences.getInt]
	 */
	override fun getInt(key: String, defaultValue: Int): Int =
		preferenceMap.getOrDefault(key, defaultValue) as Int

	/**
	 * Mocked version of [SharedPreferences.getLong]
	 */
	override fun getLong(key: String, defaultValue: Long): Long =
		preferenceMap.getOrDefault(key, defaultValue) as Long

	/**
	 * Mocked version of [SharedPreferences.getFloat]
	 */
	override fun getFloat(key: String, defaultValue: Float): Float =
		preferenceMap.getOrDefault(key, defaultValue) as Float

	/**
	 * Mocked version of [SharedPreferences.getBoolean]
	 */
	override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
		preferenceMap.getOrDefault(key, defaultValue) as Boolean

	/**
	 * Mocked version of [SharedPreferences.contains]
	 */
	override fun contains(key: String): Boolean =
		key in preferenceMap

	/**
	 * Mocked version of [SharedPreferences.edit]
	 */
	override fun edit(): SharedPreferences.Editor = preferenceEditor

	/**
	 * Mocked version of [SharedPreferences.registerOnSharedPreferenceChangeListener]
	 */
	override fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
		listeners.add(listener)
	}

	/**
	 * Mocked version of [SharedPreferences.unregisterOnSharedPreferenceChangeListener]
	 */
	override fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
		listeners.remove(listener)
	}

	/**
	 * Mock implementation of [SharedPreferences.Editor], which just saves data in memory using map.
	 */
	class MockSharedPreferenceEditor(
		private val preferenceMap: MutableMap<String, Any>,
		private val uncommittedPreferenceMap: MutableMap<String, Any>,
		private var uncommittedRemoveKeys: MutableList<String> = ArrayList()
	) : SharedPreferences.Editor {


		/**
		 * Mocked version of [SharedPreferences.Editor.putString]
		 */
		override fun putString(key: String, value: String): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.putStringSet]
		 */
		override fun putStringSet(key: String, value: Set<String>): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.putInt]
		 */
		override fun putInt(key: String, value: Int): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.putLong]
		 */
		override fun putLong(key: String, value: Long): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.putBoolean]
		 */
		override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.putBoolean]
		 */
		override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
			uncommittedPreferenceMap[key] = value
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.remove]
		 */
		override fun remove(key: String): SharedPreferences.Editor {
			uncommittedPreferenceMap.remove(key)
			uncommittedRemoveKeys.add(key)
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.clear]
		 */
		override fun clear(): SharedPreferences.Editor {
			uncommittedRemoveKeys.clear()
			preferenceMap.clear()
			uncommittedPreferenceMap.clear()
			return this
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.commit]
		 */
		override fun commit(): Boolean {
			uncommittedRemoveKeys.forEach {
				preferenceMap.remove(it)
			}

			uncommittedPreferenceMap.forEach {
				preferenceMap[it.key] = it.value
			}

			uncommittedRemoveKeys.clear()
			uncommittedPreferenceMap.clear()
			return true
		}

		/**
		 * Mocked version of [SharedPreferences.Editor.apply]
		 */
		override fun apply() {
			commit()
		}
	}
}