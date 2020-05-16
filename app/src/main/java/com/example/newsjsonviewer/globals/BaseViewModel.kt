package com.example.newsjsonviewer.globals

import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    private var idlingResource: CountingIdlingResource? = null


    fun initIdlingResource(idlingResource: CountingIdlingResource?) {
        this.idlingResource = idlingResource
    }

    protected fun incrementIdlingResource() {
        idlingResource?.increment()
    }

    protected fun decrementIdlingResource() {
        idlingResource?.decrement()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}