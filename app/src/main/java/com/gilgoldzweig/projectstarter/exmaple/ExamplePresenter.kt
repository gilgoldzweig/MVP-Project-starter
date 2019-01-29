package com.gilgoldzweig.projectstarter.exmaple

import com.gilgoldzweig.mvp.mvp.BasePresenter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * An example of a presenter that we can perform tests on
 */
open class ExamplePresenter : BasePresenter<ExampleContract.View>(),
                              ExampleContract.Presenter {

	/**
	 * The implementation of [ExampleContract.Presenter.fetchProfileName]
	 * Simple function that we can easily test
	 */
	override fun fetchProfileName() {
		launch {
			try {
				val name = fetchProfileNameFromDatabase()
				//request completed successfully

				withContext(uiContext) {
					view.onProfileNameReceived(name)
				}
			} catch (io: IOException) {
				withContext(uiContext) {
					view.onProfileNameRequestFailed(io)
				}
			}
		}
	}

	/**
	 * Example of a database operation
	 * Simple function that we can easily test
	 */
	@Throws(IOException::class)
	open suspend fun fetchProfileNameFromDatabase(): String {
		//Perform long operation that might work and might not
		return withContext(databaseContext) {
			"Gil Goldzweig"
		}
	}
}
