package com.gilgoldzweig.mvp.models.livedata

import android.arch.lifecycle.MutableLiveData

/**
 * A custom [MutableLiveData] that not only passes data but also state
 */
class StateLiveData<T> : MutableLiveData<StateData<T>>() {

    private val stateData = StateData<T>()
    /**
     * Use this to put the Data on a [StateData.DataStatus.LOADING] Status
     */
    fun postLoading() {
        postValue(stateData.loading())
    }

    /**
     * Use this to put the Data on a [StateData.DataStatus.ERROR] DataStatus
     * @param throwable the error to be handled
     */
    fun postError(throwable: Throwable) {
        postValue(stateData.error(throwable))
    }

    /**
     * Use this to put the Data on a [StateData.DataStatus.SUCCESS] DataStatus
     * @param data
     */
    fun postSuccess(data: T) {
        postValue(stateData.success(data))
    }

	/**
	 * Use this to put the Data on a [StateData.DataStatus.COMPLETED] Status
	 */
    fun postComplete() {
		postValue(stateData.completed())
    }

}
