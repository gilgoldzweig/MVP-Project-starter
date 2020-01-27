package com.gilgoldzweig.mvp.models.livedata

/**
 * Wrapper class that is able to handle different state
 *
 * @param <T> data to wrap</T>
 * */
class StateData<T> {

    var status: DataStatus = DataStatus.CREATED

    var data: T? = null

    var error: Throwable? = null

	/**
	 * Setting the status to [DataStatus.CREATED]
	 */
	fun created(): StateData<T> {
		status = DataStatus.CREATED
		data = null
		error = null
		return this
	}

	/**
	 * Setting the status to [DataStatus.LOADING]
	 */
	fun loading(): StateData<T> {
		status = DataStatus.LOADING
		data = null
		error = null
		return this
	}

	/**
	 * Setting the status to [DataStatus.SUCCESS]
	 * and assigning the data to our parameter
	 */
	fun success(data: T): StateData<T> {
		status = DataStatus.SUCCESS
		this.data = data
		error = null
		return this
	}

	/**
	 * Setting the status to [DataStatus.ERROR]
	 * and assigning the error to our parameter
	 */
	fun error(error: Throwable): StateData<T> {
		status = DataStatus.ERROR
		data = null
		this.error = error
		return this
	}

	/**
	 * Setting the status to [DataStatus.COMPLETED]
	 */
	fun completed(): StateData<T> {
		status = DataStatus.COMPLETED
		data = null
		error = null
		return this
	}


	/**
	 * Possible states of our data
	 */
	enum class DataStatus {
		CREATED,
		LOADING,
		SUCCESS,
		ERROR,
		COMPLETED
	}
}
