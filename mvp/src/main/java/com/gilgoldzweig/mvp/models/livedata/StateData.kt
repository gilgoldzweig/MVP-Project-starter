package com.gilgoldzweig.mvp.models.livedata

/**
 * Wrapper class that is able to handle different state
 *
 * @param <T> data to wrap</T>
 * */
class StateData<T> {

    var status: DataStatus
        private set

    var data: T? = null
        private set

    var error: Throwable? = null
        private set

    init {
        status = DataStatus.CREATED
        data = null
        error = null
    }

    fun loading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun success(data: T): StateData<T> {
        status = DataStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun error(error: Throwable): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.error = error
        return this
    }

    fun completed(): StateData<T> {
        status = DataStatus.COMPLETED
        data = null
        error = null
        return this
    }


    enum class DataStatus {
        CREATED,
        LOADING,
        SUCCESS,
        ERROR,
        COMPLETED
    }
}
