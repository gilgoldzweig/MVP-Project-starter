package com.gilgoldzweig.mvp.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.annotation.CallSuper
import com.gilgoldzweig.mvp.models.threads.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList
import java.util.Queue
import kotlin.coroutines.CoroutineContext

/**
 * A base presenter class that include some functionality and helping
 */
abstract class BasePresenter<V : BaseContract.View>(
	open var job: Job = Job(),
	open var dispatchers: CoroutineDispatchers = CoroutineDispatchers()) :
        CoroutineScope, LifecycleObserver, BaseContract.Presenter<V> {

    var lifecycle: Lifecycle? = null

	var autoExecuteUiActions: Boolean = true

    lateinit var view: V

	val actionsWaitingForUIExecution: Queue<V.() -> Unit> = LinkedList()

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
        this.view = view

        if (lifecycle != null) {
            bindToLifecycle(lifecycle)
        }
    }


    /**
     * Perform an action of the View on the ui context
     *
     * If a lifecycle is bound then only if it isAtLeast([Lifecycle.State.RESUMED]) and
     * we verify that the job is not cancelled
     *
     * @param addToRetryQueue In case the function is called when the provided lifecycle is not available to
     * receive the action for example [Lifecycle.State.DESTROYED]
     * the [action] will be added to a retry queue [actionsWaitingForUIExecution] and we be executed when
     * [executeQueuedUiActions] is called
     *
     * @see [Lifecycle.getCurrentState]
     */
    fun performOnUi(addToRetryQueue: Boolean = true, action: V.() -> Unit) {
        if (!job.isCancelled) {
	        if (lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) != false) {
		        launch(uiContext) {
			        view.action()
		        }
	        } else {
		        if (addToRetryQueue) {
			        actionsWaitingForUIExecution.offer(action)
		        }
	        }
        }
    }

	/**
	 * Performs all actions in [actionsWaitingForUIExecution]
	 * This function should be called when the [job] is alive and when the ui allows executing actions
	 *
	 * The function does not check for the current lifecycle state it's the caller duty to verify that
	 *
	 * @see performOnUi
	 */
	fun executeQueuedUiActions() {
		if (job.isActive) {
			runBlocking(uiContext) {
				var action = actionsWaitingForUIExecution.poll()
				while (action != null) {
					action.invoke(view)
					action = actionsWaitingForUIExecution.poll()
				}
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
	    actionsWaitingForUIExecution.clear()
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
