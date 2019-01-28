package com.gilgoldzweig.mvp.preferences

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gilgoldzweig.mvp.preferences.GlobalSharedPreferences.apply

/**
 * A way to access the same SharedPreferences file everywhere in the app without the need to provide [Context]
 */
@Suppress("unused")
object GlobalSharedPreferences {

	private lateinit var sharedPreferences: SharedPreferences
	private lateinit var editor: SharedPreferences.Editor


	/**
	 * Initialize the class with an existing SharedPreferences file
	 *
	 * @param sharedPreferences SharedPreferences that was initialized inside the application class or else the functions
	 * won't work everywhere
	 *
	 * @return instance of the GlobalSharedPreferences
	 */
	@SuppressLint("CommitPrefEdits")
	fun initialize(sharedPreferences: SharedPreferences): GlobalSharedPreferences {
		this.sharedPreferences = sharedPreferences
		this.editor = sharedPreferences.edit()
		return this
	}

	/**
	 * Initialize the class with a new shared preferences file
	 *
	 * @param application Context that will survive for the entire lifecycle of your application
	 * @param name of the SharedPreferences file to be saved. defaults to \"DefaultSharedPreferences\"
	 *
	 * @return instance of the GlobalSharedPreferences
	 */
	fun initialize(application: Application,
	               name: String = "DefaultSharedPreferences"): GlobalSharedPreferences =
			initialize(application.getSharedPreferences(name, Context.MODE_PRIVATE))


	fun getAll(): Map<String, *> = sharedPreferences.all

	fun getInt(key: String, defaultValue: Int = -1) =
			sharedPreferences.getInt(key, defaultValue)

	fun getLong(key: String, defaultValue: Long = -1L) =
			sharedPreferences.getLong(key, defaultValue)

	fun getFloat(key: String, defaultValue: Float = 0.0f) =
			sharedPreferences.getFloat(key, defaultValue)

	fun getBoolean(key: String, defaultValue: Boolean = false) =
			sharedPreferences.getBoolean(key, defaultValue)

	fun getString(key: String, defaultValue: String = ""): String =
			sharedPreferences.getString(key, defaultValue)!!

	fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): MutableSet<String>? =
			sharedPreferences.getStringSet(key, defaultValue)

	@Suppress("UNCHECKED_CAST")
	@Throws(UnsupportedOperationException::class)
	fun <T : Any> get(key: String, defaultValue: T) =
			when (defaultValue) {
				is String ->
					getString(key, defaultValue) as T

				is Int ->
					getInt(key, defaultValue) as T

				is Long ->
					getLong(key, defaultValue) as T

				is Boolean ->
					getBoolean(key, defaultValue) as T

				is Float ->
					getFloat(key, defaultValue) as T

				is Set<*> ->
					getStringSet(key, defaultValue as Set<String>) as T

				else ->
					throw UnsupportedOperationException("""
                    Exception while performing get from shared preferences
                    key: $key
                    default value: $defaultValue
                """.trimIndent())

			}


	/**
	 * Remove's the preference from the editor but does not perform commit/apply
	 * @param key The name of the preference to remove.
	 * @return the same reference so you can use one after the other
	 */
	fun remove(key: String): GlobalSharedPreferences =
			also { editor.remove(key) }


	/**
	 * Checks whether the preferences contains a preference.
	 *
	 * @param key The name of the preference to check.
	 * @return Returns true if the preference exists in the preferences,
	 *         otherwise false.
	 */
	operator fun contains(key: String): Boolean = sharedPreferences.contains(key)


	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: String): GlobalSharedPreferences =
			also { editor.putString(key, value) }


	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: Int): GlobalSharedPreferences =
			also { editor.putInt(key, value) }

	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: Long): GlobalSharedPreferences =
			also { editor.putLong(key, value) }

	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: Boolean): GlobalSharedPreferences =
			also { editor.putBoolean(key, value) }

	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: Float): GlobalSharedPreferences =
			also { editor.putFloat(key, value) }

	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 */
	fun set(key: String, value: Set<String>): GlobalSharedPreferences =
			also { editor.putStringSet(key, value) }

	/**
	 * Put's a new value to the editor but does not perform commit/apply on the editor
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return the same reference so you can use one after the other
	 * @throws ClassCastException if value is a set not of strings
	 */
	@Suppress("UNCHECKED_CAST")
	@Throws(UnsupportedOperationException::class)
	fun set(key: String, value: Any): GlobalSharedPreferences =
			also {
				when (value) {
					is String ->
						set(key, value)
					is Int ->
						set(key, value)
					is Long ->
						set(key, value)
					is Boolean ->
						set(key, value)
					is Float ->
						set(key, value)
					is Set<*> ->
						try {
							set(key, value as Set<String>)
						} catch (e: ClassCastException) {
							throw UnsupportedOperationException("""
                    Exception while performing set on shared preferences
                    key: $key
                    value: $value
                """.trimIndent())
						}

				}
			}


	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written to persistent storage.
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: String) = set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written to persistent storage.
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Int): Boolean =
			set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Long): Boolean =
			set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Boolean): Boolean =
			set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written to persistent storage.
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Float): Boolean =
			set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written to persistent storage.
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Set<String>): Boolean =
			set(key, value).commit()

	/**
	 * Set a new value to the editor and calls [commit]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @return Returns true if the new values were successfully written to persistent storage.
	 * to the [SharedPreferences]
	 */
	fun commit(key: String, value: Any): Boolean =
			set(key, value).commit()


	/**
	 * Remove's the preference from the editor but and calls [commit]
	 * @param key The name of the preference to remove.
	 * @return True if the values we're successfully removed
	 */
	fun removeAndCommit(key: String): Boolean = remove(key).commit()

	/**
	 * Calls commit on the editor
	 */
	fun commit(): Boolean = editor.commit()


	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: String) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Int) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Long) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Boolean) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Float) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Set<String>) = set(key, value).apply()

	/**
	 * Set a new value to the editor and calls [apply]
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.

	 */
	fun apply(key: String, value: Any) = set(key, value).apply()


	/**
	 * Remove's the preference from the editor and calls [apply]
	 * @param key The name of the preference to remove.
	 */
	fun removeAndApply(key: String) = remove(key).apply()

	/**
	 * Calls apply on the editor
	 */
	fun apply() = editor.apply()

}
