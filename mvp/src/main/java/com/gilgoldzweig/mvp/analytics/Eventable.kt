package com.gilgoldzweig.mvp.analytics

/**
 * Represent something that can act as an analytics event
 */
interface Eventable {
    val displayName: String

    override fun toString(): String
}

/**
 * Used to add a parameter to the analytic event
 */
interface EventParameter : Eventable

/**
 * A way for Timber to send analytic events
 */
interface Event : Eventable
