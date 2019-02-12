package com.gilgoldzweig.mvp.models.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * A way to be able to provide all the dispatchers in a single dependency
 * So it is much easier to inject using Dependency injection or just replace
 */
data class CoroutineDispatchers(
	val database: CoroutineDispatcher = Dispatchers.IO,
	val disk: CoroutineDispatcher = Dispatchers.IO,
	val network: CoroutineDispatcher = Dispatchers.IO,
	val main: CoroutineDispatcher = Dispatchers.Main,
	val default: CoroutineDispatcher = Dispatchers.Default,
	val new: CoroutineDispatcher =
		Executors.newSingleThreadExecutor().asCoroutineDispatcher()
)
