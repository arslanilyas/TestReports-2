package com.example.testreports.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testreports.utils.OneShotEvent




open class BaseAndroidViewModel() : ViewModel() {


    val snackbarMessage = MutableLiveData<OneShotEvent<String>>()
    val progressBar = MutableLiveData<OneShotEvent<Boolean>>()
    val loader = MutableLiveData<OneShotEvent<Boolean>>()




    protected fun showSnackbarMessage(message: String) {
        snackbarMessage.value = OneShotEvent(message)
    }

    protected fun showNetworkError() {
        snackbarMessage.value = OneShotEvent("Internet connection problem")
    }



    protected fun showProgressBar(show: Boolean) {
        progressBar.value = OneShotEvent(show)
    }


}