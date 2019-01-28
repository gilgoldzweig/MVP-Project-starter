package com.gilgoldzweig.projectstarter.exmaple

import com.gilgoldzweig.mvp.mvp.BaseContract

interface ExampleContract : BaseContract {

    interface View : BaseContract.View {
        /**
         * example of successful response
         */
        fun onProfileNameReceived(name: String)

        /**
         * example of a failure response
         */
        fun onProfileNameRequestFailed(exception: Exception)
    }

    interface Presenter : BaseContract.Presenter<View> {
        /**
         * example of a network/database/logic request
         * non blocking request that will run on another thread
         * and return the result using
         *
         * [View.onProfileNameReceived] for successful result
         * [View.onProfileNameRequestFailed] for failure result
         */
        fun fetchProfileName()
    }
}
