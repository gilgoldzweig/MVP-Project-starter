package com.gilgoldzweig.mvp.models.livedata

import android.arch.lifecycle.MutableLiveData

/**
 * A custom [MutableLiveData] that not only passes data but also state
 */
@Deprecated(
    "We be refactored to ImmutableStateLiveData in future release",
    ReplaceWith(
        "MutableStateLiveData",
        "com.gilgoldzweig.mvp.models.livedata.MutableStateLiveData"
    )
)
class StateLiveData<T> : MutableStateLiveData<T>()