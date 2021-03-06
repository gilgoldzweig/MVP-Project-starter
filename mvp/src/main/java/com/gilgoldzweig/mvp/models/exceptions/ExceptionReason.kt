package com.gilgoldzweig.mvp.models.exceptions

/**
 * A way for the presenter to tell the adapter what was the issue.
 *
 * Whenever we want to notify the [com.gilgoldzweig.mvp.BaseContract.View] about an exception
 * without it handling the actual [Exception] so we can encapsulate it in a way that the will will know
 * only the information that the user can benefit from
 *
 * example:
 * class DemoView : DemoContract.View {
 *
 *   lateinit var presenter: DemoContract.Presenter
 *
 *   fun main() {
 *      presenter.performNetworkRequest()
 *   }
 *
 *   ...
 *
 *   override fun onNetworkRequestFailed(reason: ExceptionReason) {
 *      when (reason) {
 *          is NoNetworkReason -> {
 *              //Update the user that he doesn't have internet connection
 *          }
 *      }
 *   }
 *
 * }
 *
 *
 * @see [com.gilgoldzweig.mvp.BaseContract.Presenter]
 */
interface ExceptionReason

/**
 * The request failed because there is no network
 */
open class NoNetworkReason : ExceptionReason

