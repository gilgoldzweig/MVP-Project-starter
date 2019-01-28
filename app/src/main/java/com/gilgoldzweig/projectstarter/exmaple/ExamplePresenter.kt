package com.gilgoldzweig.projectstarter.exmaple

import com.gilgoldzweig.mvp.mvp.BasePresenter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

open class ExamplePresenter : BasePresenter<ExampleContract.View>(),
	ExampleContract.Presenter {


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

	@Throws(IOException::class)
	open suspend fun fetchProfileNameFromDatabase(): String {
		//Perform long operation that might work and might not
		return withContext(databaseContext) {
			"Gil Goldzweig"
		}
	}
}
