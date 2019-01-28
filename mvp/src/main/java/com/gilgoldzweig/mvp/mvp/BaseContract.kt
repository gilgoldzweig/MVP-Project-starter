package com.gilgoldzweig.mvp.mvp

import android.arch.lifecycle.Lifecycle
import android.support.annotation.UiThread

/**
 * A contract between a view(ie: Activity, Fragment) and a presenter handling the logic of the view
 */
interface BaseContract {

	/**
	 * The view's part of the contract
	 * all functions that the presenter can call and return values will be here
	 *
	 * It's the presenter's job to make sure that all calls to the view we be on the UI thread
	 *
	 * example:
	 *
	 * interface ViewImpl : BaseContract.View {
	 *
	 *    fun onNetworkRequestCompletedSuccessfully(data: Data)
	 *
	 *    fun onNetworkRequestFailed(reason: ExceptionReason)
	 * }
	 */
	@UiThread
	interface View


	/**
	 * The presenter's part of the contract
	 * all functions that the view can call to perform actions
	 *
	 * The presenter needs to return the actions on the UI thread
	 *
	 * example:
	 *
	 * interface PresenterImpl : BaseContract.Presenter<ViewImpl> {
	 *
	 *    fun performNetworkRequest()
	 *
	 * }
	 */
	interface Presenter<V : View> {


		/**
		 * The View's way to be attached to the presenter
		 * @param lifecycle if a lifecycle is provided the presenter will be bound to it, and be able to detach automatically
		 */
		fun attach(view: V, lifecycle: Lifecycle? = null)

		/**
		 * Detach's the view from the presenter and verify that all processes running are killed,
		 * remove all references of the view
		 */
		fun detach()
	}

}
