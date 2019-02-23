package com.gilgoldzweig.mvp.mvp

import com.gilgoldzweig.mvp.models.threads.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
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

	fun tearDown() {
		Dispatchers.resetMain()
	}
}