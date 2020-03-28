package com.example.newsjsonviewer.globals

import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    protected var idlingResource: CountingIdlingResource? = null


    fun initIdlingResource(idlingResource: CountingIdlingResource?) {
        this.idlingResource = idlingResource
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}