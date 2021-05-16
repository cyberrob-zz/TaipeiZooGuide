package com.wang.taipeizooguide.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.wang.taipeizooguide.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseViewModelTest {
    @get:Rule
    open val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    open val testCoroutineRule = CoroutineTestRule()

    @MockK
    protected lateinit var owner: LifecycleOwner

    private lateinit var lifecycle: LifecycleRegistry

    @Before
    open fun setup() {
        MockKAnnotations.init(this)

        lifecycle = LifecycleRegistry(owner)
        every { owner.lifecycle } returns lifecycle

        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    protected fun initCoroutine(vm: BaseViewModel) {
        vm.apply {
            setViewModelScope(testCoroutineRule.testCoroutineScope)
            setCoroutineContext(testCoroutineRule.testCoroutineDispatcher)
        }
    }

    @ExperimentalCoroutinesApi
    protected fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineRule.runBlockingTest(block)


    protected fun launchTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineRule.testCoroutineScope.launch(testCoroutineRule.testCoroutineDispatcher) { block }

}