package com.gilgoldzweig.projectstarter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gilgoldzweig.projectstarter.exmaple.ExampleContract

/**
 * Example main activity showing how the view will be implemented
 */
class MainActivity : AppCompatActivity(), ExampleContract.View {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	/**
	 * Example of how the view with receive calls from the presenter
	 */
	override fun onProfileNameReceived(name: String) = Unit

	/**
	 * Example of how the view with receive calls from the presenter
	 */
	override fun onProfileNameRequestFailed(exception: Exception) = Unit
}
