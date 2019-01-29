package com.gilgoldzweig.projectstarter.example

import com.gilgoldzweig.mvp.models.threads.CoroutineDispatchers
import com.gilgoldzweig.projectstarter.exmaple.ExampleContract
import com.gilgoldzweig.projectstarter.exmaple.ExamplePresenter
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.concurrent.Executors

/**
 * Tests [ExamplePresenter]
 */
@RunWith(MockitoJUnitRunner::class)
class ExamplePresenterTest {

	/**
	 * We don't need the real view just the functions in the contract
	 * So we can just mock it
	 */
	@Mock
	private lateinit var view: ExampleContract.View

	/**
	 * Mocked exception so we can check that we can throw and see if it's chained properly
	 */
	@Mock
	private lateinit var mockedException: IOException

	/**
	 * our presenter
	 * with @Spy so we can verify that it calls that write functions on the view
	 */
	@Spy
	private var presenter: ExamplePresenter = ExamplePresenter()

	/**
	 * We replace the original CoroutineDispatchers with one that don't actually use the main thread
	 */
	private val coroutineDispatchers = CoroutineDispatchers(
		main = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
	)

	/**
	 * Attaching our mocked view to our presenter and we replace the original dispatchers with our custom one
	 */
	@Before
	fun setUp() {
		presenter.attach(view)
		`when`(presenter.dispatchers).thenReturn(coroutineDispatchers)
	}

	/**
	 * Testing both success and failure state of [ExamplePresenter.fetchProfileName]
	 * we want the first call to return a successful value and the second one with an exception
	 */
	@Test
	fun testFetchProfileName() {
		//Because we are calling a suspend method we wrap it in a
		//runBlocking
		runBlocking {

			`when`(presenter.fetchProfileNameFromDatabase())
				.thenReturn("Gil Goldzweig") //We want to test success case
				.thenThrow(mockedException) //We want to verify failure case


			//We call the function that we are testing one for success and one for failure
			presenter.fetchProfileName()
			presenter.fetchProfileName()

			verifySuccessfulFetchProfileName()
			verifyFailureFetchProfileName()

		}
	}

	/**
	 * We want to verify that in case we receive a positive response
	 * then onProfileNameReceived is called on our view
	 */
	private fun verifySuccessfulFetchProfileName() {
		verify(view, times(1))
			.onProfileNameReceived("Gil Goldzweig")

	}

	/**
	 * We want to verify that in case we receive a failure response
	 * then onProfileNameRequestFailed is called on our view
	 */
	private fun verifyFailureFetchProfileName() {
		verify(view, times(1))
			.onProfileNameRequestFailed(mockedException)
	}

	/**
	 * We reset our mocked views
	 */
	@After
	fun tearDown() {
		Mockito.reset(view, mockedException, presenter)
	}
}