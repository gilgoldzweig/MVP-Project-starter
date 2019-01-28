package com.gilgoldzweig.mvp.analytics

import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import java.util.ArrayList
import kotlin.reflect.KClass

/**
 * Builder pattern adaptation for [android.os.Bundle] makes it easy to build and create bundles
 */
@Suppress("unused")
class Bundlify(var bundle: Bundle = Bundle()) {

	/**
	 * @receiver the key
	 * @return the associated [String] [Array] or null if no value is found
	 */
	fun String.getStringArray(): Array<out String>? =
			bundle.getStringArray(this)

	/**
	 * @receiver the key
	 * @return the associated [String] [ArrayList] or null if no value is found
	 */
	fun String.getStringArrayList(): ArrayList<out String>? =
			bundle.getStringArrayList(this)

	/**
	 * @receiver the key
	 * @return the associated [Int] or 0 if no value is found
	 */
	fun String.getInt(): Int =
			bundle.getInt(this)

	/**
	 * @receiver the key
	 * @return the associated [Long] or 0L if no value is found
	 */
	fun String.getLong(): Long =
			bundle.getLong(this)

	/**
	 * @receiver the key
	 * @return the associated [Float] or 0.0f if no value is found
	 */
	fun String.getFloat(): Float =
			bundle.getFloat(this)

	/**
	 * @receiver the key
	 * @return the associated [Boolean] or false if no value is found
	 */
	fun String.getBoolean(): Boolean =
			bundle.getBoolean(this)

	/**
	 * @receiver the key
	 * @return the associated [String] or null if no value is found
	 */
	fun String.getString(): String? =
			bundle.getString(this)

	/**
	 * @receiver the key
	 * @return the associated [Parcelable] casted as nullable [T] or null if no value is found
	 */
	fun <T : Parcelable> String.getParcelable(): T? =
			bundle.getParcelable(this) as? T

	/**
	 * @receiver the key
	 * @return the associated [Parcelable] casted as nullable [T] [Array] or null if no value is found
	 * @throws ClassCastException
	 */
	@Throws(ClassCastException::class)
	fun <T : Parcelable> String.getParcelableArray(): Array<T>? =
			bundle.getParcelableArray(this) as? Array<T>

	/**
	 * @receiver the key
	 * @return the associated [Parcelable] casted as nullable [T] [ArrayList] or null if no value is found
	 */
	fun <T : Parcelable> String.getParcelableArrayList(): ArrayList<T>? =
			bundle.getParcelableArrayList<T>(this)

	/**
	 * Automatically get the value from the bundle based on the key
	 *
	 * @param type the type of the returned value
	 *
	 * @return the associated [T] or the default value if no value is found
	 * @throws ClassCastException
	 */
	@Throws(ClassCastException::class)
	@Suppress("UNCHECKED_CAST")
	fun <T : Any> get(type: KClass<T>, key: String): T? =
			when (type) {
				String::class ->
					key.getString() as? T

				Int::class ->
					key.getInt() as? T

				Long::class ->
					key.getLong() as? T

				Boolean::class ->
					key.getBoolean() as? T

				Float::class ->
					key.getFloat() as? T

				Parcelable::class ->
					key.getParcelable<Parcelable>() as? T

				Array<Parcelable>::class ->
					key.getParcelableArray<Parcelable>() as? T

				Array<String>::class ->
					key.getStringArray() as? T

				else -> throw ClassCastException("Bundlify only support bundle types")
			}

	/**
	 * Checks if the bundle is empty
	 */
	fun isEmpty(): Boolean =
			bundle.isEmpty


	/**
	 * Operator overloading to check if the bundle contains a key
	 *
	 * example:
	 *
	 * val bundlify = Bundlify()
	 *
	 *  "somekey" in bundlify
	 *
	 * the same as
	 *
	 * bundlify.bundle.containsKey("somekey")
	 *
	 *
	 */
	operator fun contains(key: String): Boolean = key in bundle

	/**
	 * Add's a new [String] to the bundle
	 */
	fun put(key: String, value: String): Bundlify {
		bundle.putString(key, value)
		return this
	}

	/**
	 * Add's a new [Int] to the bundle
	 */
	fun put(key: String, value: Int): Bundlify {
		bundle.putInt(key, value)
		return this
	}

	/**
	 * Add's a new [Long] to the bundle
	 */
	fun put(key: String, value: Long): Bundlify {
		bundle.putLong(key, value)
		return this
	}

	/**
	 * Add's a new [Boolean] to the bundle
	 */
	fun put(key: String, value: Boolean): Bundlify {
		bundle.putBoolean(key, value)
		return this
	}

	/**
	 * Add's a new [Float] to the bundle
	 */
	fun put(key: String, value: Float): Bundlify {
		bundle.putFloat(key, value)
		return this
	}

	/**
	 * Add's a new [Parcelable] to the bundle
	 */
	fun put(key: String, value: Parcelable): Bundlify {
		bundle.putParcelable(key, value)
		return this
	}

	/**
	 * Add's a new [Parcelable] [Array] to the bundle
	 */
	fun put(key: String, value: Array<Parcelable>): Bundlify {
		bundle.putParcelableArray(key, value)
		return this
	}

	/**
	 * Add's a new [Parcelable] [ArrayList] to the bundle
	 */
	fun put(key: String, value: ArrayList<Parcelable>): Bundlify {
		bundle.putParcelableArrayList(key, value)
		return this
	}

	/**
	 * Add's the display name of the [Eventable] to the bundle
	 */
	fun put(eventable: Eventable, value: Any): Bundlify {
		put(eventable.toString(), value)
		return this

	}

	/**
	 * Add's a new value to the bundle
	 * based on the type of it
	 */
	@Throws(ClassCastException::class)
	@Suppress("UNCHECKED_CAST")
	fun put(key: String, value: Any) {
		when (value) {
			is String ->
				put(key, value)
			is Int ->
				put(key, value)
			is Long ->
				put(key, value)
			is Boolean ->
				put(key, value)
			is Float ->
				put(key, value)
			is Parcelable ->
				put(key, value)
			is Array<*> ->
				put(key, value as Array<Parcelable>)
			is ArrayList<*> ->
				put(key, value as ArrayList<Parcelable>)
		}
	}

	operator fun String.plusAssign(value: Any) =
			put(this, value).ignoreResult()

	operator fun plusAssign(keyValuePair: Pair<String, Any>) =
			put(keyValuePair.first, keyValuePair.second).ignoreResult()

	/**
	 * Removes the value associated with the key
	 */
	fun remove(key: String): Bundlify {
		bundle.remove(key)
		return this
	}

	operator fun minus(key: String) = remove(key)

	override fun toString(): String =
			bundle.toString()

	private fun Any?.ignoreResult() = Unit
}

/**
 * DSL for creating [android.os.Bundle] using Bundlify
 */
inline fun bundle(bundle: Bundlify.() -> Unit) = Bundlify().apply(bundle).bundle

/**
 * Operator overloading to check if the bundle contains a key
 *
 * example:
 *
 * val bundle = Bundle()
 *
 *  "somekey" in bundle
 *
 * the same as
 *
 * bundle.containsKey("somekey")
 *
 *
 */
operator fun Bundle.contains(key: String) = containsKey(key)
