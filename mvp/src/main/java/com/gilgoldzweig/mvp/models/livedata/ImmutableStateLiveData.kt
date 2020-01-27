package com.gilgoldzweig.mvp.models.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * A custom [LiveData] wrapper to wrap LiveData<StateData<T>>
 * The class will be renamed to StateLiveData in future release
 */
class ImmutableStateLiveData<T> : LiveData<StateData<T>>()