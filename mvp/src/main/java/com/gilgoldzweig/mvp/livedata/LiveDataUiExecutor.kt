package com.gilgoldzweig.mvp.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.gilgoldzweig.mvp.consts.UiTask
import com.gilgoldzweig.mvp.mvp.BaseContract

class LiveDataUiExecutor<V : BaseContract.View> {

    private val tasksLiveData = MutableLiveData<UiTask<V>>()
    private var lifecycleOwner: LifecycleOwner? = null

    /**
     * Observe the tasks live data and execute the actions sent
     */
    fun observe(view: V, lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        tasksLiveData.observe(lifecycleOwner, Observer {
            if (it == null) return@Observer
            it.invoke(view)
        })
    }

    /**
     * Post a UI task to the tasks live data
     */
    fun postUiTask(task: UiTask<V>) {
        tasksLiveData.postValue(task)
    }

    /**
     * Removes the observers from the lifecycle owner that that was attached on [observe]
     */
    fun removeObservers() {
        lifecycleOwner?.let {
            tasksLiveData.removeObservers(it)
        }
    }
}