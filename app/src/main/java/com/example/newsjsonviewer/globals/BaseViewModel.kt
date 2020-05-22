package com.example.newsjsonviewer.globals

import androidx.lifecycle.ViewModel
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.newsjsonviewer.uitest.IdlingResourceProvider
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    /**
     * Idling resource for this ViewModel instance. It will be identified by the name of the
     * implementing ViewModel
     */
    private var idlingResource: CountingIdlingResource? =
        IdlingResourceProvider.provideIdlingResource(this.javaClass.name)


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

    fun getIdlingResource() = idlingResource

}