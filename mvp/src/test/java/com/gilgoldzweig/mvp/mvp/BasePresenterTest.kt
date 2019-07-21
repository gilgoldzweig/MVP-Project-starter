package com.gilgoldzweig.mvp.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BasePresenterTest {

    @Mock
    private lateinit var baseView: BaseContractUnderTest.View

    @Spy
    private var basePresenter: BasePresenter<BaseContractUnderTest.View> = BasePresenterUnderTest()

    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycleRegistry: LifecycleRegistry

    private val mainThreadSurrogate: ExecutorCoroutineDispatcher =
        newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

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
        basePresenter.bindToLifecycle(lifecycleRegistry)

        assertNotNull(basePresenter.lifecycle)

        assert(lifecycleRegistry.observerCount == 1)
    }

    @Test
    fun testPerformOnUiThreadCallAction() {
        basePresenter.performOnUi {
            performOnUiCallTest()
            verify(baseView, times(1)).performOnUiCallTest()
        }
    }

    @Test
    fun testPerformOnUiThreadLifecycleStateFail() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        basePresenter.bindToLifecycle(lifecycleRegistry)
        basePresenter.performOnUi {
            performOnUiCallTest()
            verify(baseView, never()).performOnUiCallTest()
        }
    }

    @Test
    fun testPerformOnUiThreadRetryActionsAddedWhenLifecycleStateFail() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        basePresenter.bindToLifecycle(lifecycleRegistry)
        basePresenter.performOnUi {
            performOnUiCallTest()
        }
        assertTrue(basePresenter.actionsWaitingForUIExecution.isNotEmpty())

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        runBlocking {
            basePresenter.executeQueuedUiActions()
        }
        assertTrue(basePresenter.actionsWaitingForUIExecution.isEmpty())
        verify(baseView, times(1)).performOnUiCallTest()
    }

    @Test
    fun testExecuteOnUiThreadCallAction() {
        runBlocking {
            basePresenter.executeOnUi {
                performOnUiCallTest()
            }
            verify(baseView, times(1)).performOnUiCallTest()
        }
    }

    @Test
    fun testExecuteOnUiThreadLifecycleStateFail() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        basePresenter.bindToLifecycle(lifecycleRegistry)
        runBlocking {
            basePresenter.executeOnUi {
                performOnUiCallTest()
            }
            verify(baseView, never()).performOnUiCallTest()
        }
    }

    @Test
    fun testExecuteOnUiThreadRetryActionsAddedWhenLifecycleStateFail() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        basePresenter.bindToLifecycle(lifecycleRegistry)
        runBlocking {
            repeat(3) {
                basePresenter.executeOnUi {
                    performOnUiCallTest()
                }
            }
        }
        assertTrue(basePresenter.actionsWaitingForUIExecution.isNotEmpty())

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        runBlocking {
            basePresenter.executeQueuedUiActions()
        }
        assertTrue(basePresenter.actionsWaitingForUIExecution.isEmpty())
        verify(baseView, times(3)).performOnUiCallTest()
    }

    @Test
    fun testLifecycleDetachCalled() {
        basePresenter.bindToLifecycle(lifecycleRegistry)

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        assert(lifecycleRegistry.observerCount == 0)

        assertNull(basePresenter.lifecycle)
    }

    @After
    fun tearDown() {
        reset(basePresenter, baseView, lifecycleOwner)
        Dispatchers.resetMain()
    }
}