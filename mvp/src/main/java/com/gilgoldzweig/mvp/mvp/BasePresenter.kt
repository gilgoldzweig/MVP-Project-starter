package com.gilgoldzweig.mvp.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.annotation.CallSuper
import com.gilgoldzweig.mvp.models.threads.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * A base presenter class that include some functionality and helping
 */
abstract class BasePresenter<V : BaseContract.View> :
        CoroutineScope, LifecycleObserver, BaseContract.Presenter<V> {


    open var job: Job = Job()

    var lifecycle: Lifecycle? = null

    lateinit var view: V

    open var dispatchers: CoroutineDispatchers = CoroutineDispatchers()

    override val coroutineContext: CoroutineContext
        get() = dispatchers.default + job

    val networkContext: CoroutineContext
        get() = dispatchers.network + job

    val uiContext: CoroutineContext
        get() = dispatchers.main + job

    val databaseContext: CoroutineContext
        get() = dispatchers.database + job


    /**
     * attach the view to the presenter
     * creates a new job if the old one was cancelled
     *
     * @param view, view to bind
     * @param lifecycle a lifecycle we can bind
     */
    override fun attach(view: V, lifecycle: Lifecycle?) {
        if (job.isCancelled) {
            job = Job()
        }

        this.view = view

        if (lifecycle != null) {
            bindToLifecycle(lifecycle)
        }
    }


    /**
     * Perform an action of the View on the ui context
     *
     * If a lifecycle is bound then only isAtLeast([Lifecycle.State.STARTED]) and
     * we verify that the job is not cancelled
     * @see [Lifecycle.getCurrentState]
     */
    fun performOnUi(action: V.() -> Unit) {
        if (!job.isCancelled &&
            lifecycle?.currentState?.isAtLeast(Lifecycle.State.STARTED) != false) {

            launch(uiContext) {
                view.action()
            }
        }
    }

	/**
	 * Binds the class to a lifecycle
	 * and calls [Lifecycle.addObserver] with this class as our observer
	 */
	@CallSuper
	fun bindToLifecycle(lifecycle: Lifecycle) {
		this.lifecycle = lifecycle
		lifecycle.addObserver(this)
	}

    /**
     * Stops all running jobs and remove the lifecycle observer if it exist
     */
    @CallSuper
    override fun detach() {
        job.cancel()
        lifecycle?.removeObserver(this)
        lifecycle = null
    }

	/**
	 * If this class is bound to a lifecycle then we can listen to lifecycle events
	 * There is no default implementation but inheritors can use this function easily
	 */
	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	open fun onStart() = Unit

	/**
	 * If this class is bound to a lifecycle then we can listen to lifecycle events
	 * There is no default implementation but inheritors can use this function easily
	 */
	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	open fun onStop() = Unit

	/**
	 * If this class is bound to a lifecycle then we can listen to the [Lifecycle.Event.ON_DESTROY]
	 * and automatically detaching
	 */
	@CallSuper
	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	open fun onDestroy() {
		detach()
	}
}
