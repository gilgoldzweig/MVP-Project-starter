package com.gilgoldzweig.mvp.preferences

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * A way to access SharedPreferences everywhere in the app without the need to provide [Context]
 */
@Suppress("unused")
object GlobalSharedPreferences {

    private lateinit var sharedPreferences: SharedPreferences
    internal lateinit var editor: SharedPreferences.Editor

    /**
     * Initialize the class with an existing SharedPreferences file
     *
     * @param sharedPreferences Initialized inside the application class or else the functions
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
    fun initialize(
        application: Application,
        name: String = "DefaultSharedPreferences"
    ): GlobalSharedPreferences =
        initialize(application.getSharedPreferences(name, Context.MODE_PRIVATE))

    /**
     * Returns all the values currently in the shared preferences
     */
    fun getAll(): Map<String, *> = sharedPreferences.all

    /**
     * Retrieve an [Int] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getInt(key: String, defaultValue: Int = -1): Int =
        sharedPreferences.getInt(key, defaultValue)

    /**
     * Retrieve a [Long] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getLong(key: String, defaultValue: Long = -1L): Long =
        sharedPreferences.getLong(key, defaultValue)

    /**
     * Retrieve a [Float] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getFloat(key: String, defaultValue: Float = 0.0f): Float =
        sharedPreferences.getFloat(key, defaultValue)

    /**
     * Retrieve an [Boolean] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    /**
     * Retrieve an [String] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getString(key: String, defaultValue: String = ""): String? =
        sharedPreferences.getString(key, defaultValue)

    /**
     * Retrieve an [Set<String>] value from the preferences.
     * based on the provided
     * @param key
     * @return the int associated with the key or the default value if no preference is found
     */
    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): MutableSet<String>? =
        sharedPreferences.getStringSet(key, defaultValue)

    /**
     * Retrieve a value from the preferences.
     * based on the provided
     * @param key
     * and cast it to the defaultValue type
     * @return the int associated with the key or the default value if no preference is found
     * @throws UnsupportedOperationException if the type of defaultValue is not a support type
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class)
    fun <T : Any> get(key: String, defaultValue: T): T =
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
                throw UnsupportedOperationException(
                    """
                    Exception while performing get from shared preferences
                    key: $key
                    default value: $defaultValue
                """.trimIndent()
                )
        }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     *         otherwise false.
     */
    operator fun contains(key: String): Boolean = sharedPreferences.contains(key)

    @Suppress("unused")
    internal object Editor {

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: String): Editor =
            also { editor.putString(key, value) }

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: Int): Editor =
            also { editor.putInt(key, value) }

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: Long): Editor =
            also { editor.putLong(key, value) }

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: Boolean): Editor =
            also { editor.putBoolean(key, value) }

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: Float): Editor =
            also { editor.putFloat(key, value) }

        /**
         * Put's a new value to the editor but does not perform commit/apply on the editor
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return the same reference so you can use one after the other
         */
        fun set(key: String, value: Set<String>): Editor =
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
        fun <T : Any> set(key: String, value: T): Editor =
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
                            throw UnsupportedOperationException(
                                """
                    Exception while performing set on shared preferences
                    key: $key
                    value: $value
                """.trimIndent()
                            )
                        }
                }
            }

        /**
         * Remove's the preference from the editor but does not perform commit/apply
         * @param key The name of the preference to remove.
         * @return the same reference so you can use one after the other
         */
        fun remove(key: String): Editor =
            also { editor.remove(key) }

        /**
         * Set a new value to the editor and calls [commit]
         * @param key The name of the preference to modify.
         * @param value The new value for the preference.
         * @return Returns true if the new values were successfully written to persistent storage.
         * to the [SharedPreferences]
         */
        fun commit(key: String, value: String): Boolean = set(key, value).commit()

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
         * Calls commit on the editor
         */
        fun commit(): Boolean = editor.commit()

        /**
         * Calls apply on the editor
         */
        fun apply() = editor.apply()

        inline fun commit(commit: Editor.() -> Unit) {
            commit.invoke(this)
            commit()
        }

        inline fun apply(apply: Editor.() -> Unit) {
            apply.invoke(this)
            apply()
        }
    }
}
