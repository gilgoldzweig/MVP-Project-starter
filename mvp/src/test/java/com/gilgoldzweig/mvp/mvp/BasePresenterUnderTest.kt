package com.gilgoldzweig.mvp.mvp

import com.gilgoldzweig.mvp.models.threads.CoroutineDispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * Test contract dummy under test
 */
interface BaseContractUnderTest {

	/**
	 * Test contract dummy under test
	 */
	interface View : BaseContract.View {
		fun performOnUiCallTest()
	}

	/**
	 * Test contract dummy under test
	 */
	interface Presenter : BaseContract.Presenter<View>
}

/**
 * Test BasePresenter dummy under test
 */
open class BasePresenterUnderTest : BasePresenter<BaseContractUnderTest.View>(),
                                    BaseContractUnderTest.Presenter {
	/**
	 * We replace the original CoroutineDispatchers with one that don't actually use the main thread
	 */
	override var dispatchers = CoroutineDispatchers(
		main = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
	)
}