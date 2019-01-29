package com.gilgoldzweig.mvp.logging

import android.os.Build
import com.gilgoldzweig.mvp.analytics.Bundlify
import com.gilgoldzweig.mvp.analytics.Event
import com.gilgoldzweig.mvp.analytics.EventParameter
import java.util.regex.Pattern

private const val MAX_TAG_LENGTH = 23
private const val CALL_STACK_INDEX = 4
private val anonymousClassPattern = Pattern.compile("(\\$\\d+)+$")

/**
 * Calls [Timber.d] with a message based on the receiver
 */
@Suppress("unused")
fun Any?.d(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
): Unit =
	Timber.tag(getTag()).d(createMessage(prefix, postfix, separator))

/**
 * Calls [Timber.e] with a message based on the receiver
 */
@Suppress("unused")
fun Any?.e(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
): Unit =
	Timber.tag(getTag()).e(createMessage(prefix, postfix, separator))

/**
 * Calls [Timber.i] with a message based on the receiver
 */
@Suppress("unused")
fun Any?.i(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
): Unit = Timber.tag(getTag()).i(createMessage(prefix, postfix, separator))

/**
 * Calls [Timber.wtf] with a message based on the receiver
 */
@Suppress("unused")
fun Any?.wtf(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
) =
	Timber.tag(getTag())
		.wtf(createMessage(prefix, postfix, separator))

/**
 * Calls [Timber.w] with a message based on the receiver
 */
@Suppress("unused")
fun Any?.w(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
) = Timber.tag(getTag())
	.w(createMessage(prefix, postfix, separator))


private fun Any?.createMessage(
	prefix: String = "",
	postfix: String = "",
	separator: String = " "
): String =
	"$prefix$separator${this ?: "null"}$separator$postfix"

/**
 * logs a non fetal exception
 */
@Suppress("unused")
fun Throwable.crash(message: String? = null) {
	if (message == null) {
		Timber.tag(getTag()).crash(this)
	} else {
		Timber.tag(getTag()).crash(this, message)
	}
}

/**
 * logs an analytics event to Timber without extra parameters
 */
@Suppress("unused")
fun Event.event(): Unit = Timber.event(this)

/**
 * logs an analytics event to Timber with extra parameters
 */
fun Event.event(vararg params: Pair<EventParameter, Any>) {
	val bundlify = Bundlify()
	params.forEach { bundlify.put(it.first, it.second) }
	Timber.tag(getTag()).event(this, bundlify)
}

/**
 * Extract the tag which should be used for the message from the `element`. By default
 * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
 * becomes `Foo`).
 *
 *
 * Note: This will not be called if a [manual tag][.tag] was specified.
 */
private fun createStackElementTag(element: StackTraceElement): String {
	var tag = element.className
	val m = anonymousClassPattern.matcher(tag)
	if (m.find()) {
		tag = m.replaceAll("")
	}
	tag = tag.substring(tag.lastIndexOf('.') + 1)
	// Tag length limit was removed in API 24.
	return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		tag
	} else tag.substring(0, MAX_TAG_LENGTH)
}

/**
 * Returns the tag for the place that called the function
 */
private fun getTag(): String {
	val stackTrace = Throwable().stackTrace
	if (stackTrace.size <= CALL_STACK_INDEX) {
		if (stackTrace.isNotEmpty()) {
			return createStackElementTag(stackTrace.last())
		}
	}
	return createStackElementTag(stackTrace[CALL_STACK_INDEX])
}
