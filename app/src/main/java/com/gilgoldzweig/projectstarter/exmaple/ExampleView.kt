package com.gilgoldzweig.projectstarter.exmaple

class ExampleView : ExampleContract.View {

	override fun onProfileNameReceived(name: String) {
		//Some action
	}

	override fun onProfileNameRequestFailed(exception: Exception) {
		//Some action
	}
}