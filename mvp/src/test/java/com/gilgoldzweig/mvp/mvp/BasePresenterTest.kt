package com.gilgoldzweig.mvp.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.gilgoldzweig.mvp.mvp.test.BasePresenterTestImpl
import com.gilgoldzweig.mvp.mvp.test.BasePresenterTestViewImpl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

	@Mock
	private lateinit var baseView: BasePresenterTestViewImpl

	@Mock
	private lateinit var lifecycleOwner: LifecycleOwner

	private lateinit var lifecycleRegistry: LifecycleRegistry

	private lateinit var basePresenter: BasePresenterTestImpl

	@Before
	fun setUp() {
		basePresenter = BasePresenterTestImpl()

		lifecycleRegistry = LifecycleRegistry(lifecycleOwner)

		lifecycleRegistry.markState(Lifecycle.State.CREATED)

		basePresenter.attach(baseView, null)
	}

	@Test
	fun testDetach() {

		assertFalse(basePresenter.job.isCancelled)

		basePresenter.detach()

		assertTrue(basePresenter.job.isCancelled)
	}

	@Test
	fun testLifecycleBind() {
		basePresenter.attach(baseView, lifecycleRegistry)

		assertNotNull(basePresenter.lifecycle)

		assert(lifecycleRegistry.observerCount == 1)
	}

	@Test
	fun testLifecycleDetachCalled() {
		basePresenter.attach(baseView, lifecycleRegistry)

		lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

		assert(lifecycleRegistry.observerCount == 0)

		assertNull(basePresenter.lifecycle)
	}
}