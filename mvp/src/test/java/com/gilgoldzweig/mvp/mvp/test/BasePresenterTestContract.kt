package com.gilgoldzweig.mvp.mvp.test

import com.gilgoldzweig.mvp.mvp.BaseContract

interface BasePresenterTestContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View>
}
