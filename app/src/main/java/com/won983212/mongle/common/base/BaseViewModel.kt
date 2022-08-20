package com.won983212.mongle.common.base

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.util.SingleLiveEvent
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.common.util.toastShort
import com.won983212.mongle.data.remote.api.RequestErrorType
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.view.openLoadingDialog

open class BaseViewModel : ViewModel(), RequestLifecycleCallback {
    private val _eventErrorMessage = SingleLiveEvent<String>()
    val eventErrorMessage = _eventErrorMessage.asLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.asLiveData()

    private var loadingDialog: AlertDialog? = null


    protected fun setLoading(loading: Boolean) {
        _isLoading.postValue(loading)
    }

    protected fun setError(msg: String) {
        _eventErrorMessage.postValue(msg)
    }

    fun attachDefaultErrorHandler(context: ComponentActivity) {
        eventErrorMessage.observe(context) {
            context.toastShort(it)
        }
    }

    fun attachDefaultLoadingHandler(context: ComponentActivity) {
        isLoading.observe(context) {
            if (it) {
                loadingDialog?.dismiss()
                loadingDialog = openLoadingDialog(context)
            } else {
                loadingDialog?.dismiss()
            }
        }
    }

    override fun onStart() {
        setLoading(true)
    }

    override fun onComplete() {
        setLoading(false)
    }

    override fun onError(requestErrorType: RequestErrorType, msg: String) {
        setError(msg)
        setLoading(false)
    }
}