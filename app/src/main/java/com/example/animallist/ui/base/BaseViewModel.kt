package com.example.animallist.ui.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.animallist.R
import com.example.animallist.global.helpers.DebugLog
import com.example.animallist.global.helpers.SingleEventLiveDataEvent
import com.example.animallist.global.utils.NoInternetException
import com.example.animallist.ui.data.DialogAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel(
    private val app: Application
) : AndroidViewModel(app) {


    protected val context: Context
        get() = app.applicationContext

    private val _showProgressBar = SingleEventLiveDataEvent<DialogAction>()
    val showProgressBar: LiveData<DialogAction>
        get() = _showProgressBar

    private val _showToast: SingleEventLiveDataEvent<String> = SingleEventLiveDataEvent()
    val showToast: LiveData<String>
        get() = _showToast

    private val _showSnackBar = SingleEventLiveDataEvent<String>()
    val showSnackBar: LiveData<String>
        get() = _showSnackBar

    fun showBlockingProgressBar() {
        _showProgressBar.value = DialogAction.SHOW_BLOCKING
    }

    fun hideProgressBar() {
        _showProgressBar.value = DialogAction.DISMISS
    }


    fun showToast(message: String) {
        _showToast.value = message
    }

    fun showSnackBar(msg: String) {
        _showSnackBar.value = msg
    }


    fun showNetworkError(
        okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        retry: Boolean = false
    ) {
       showToast("No network Available")

    }


    fun showServerError(
        okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        retry: Boolean = false
    ) {

         showToast("Server Error")

    }

    fun handleApiRequestFailureWithRefresh(
        action: () -> Unit,
        error: Throwable,
        handleRequestError: Boolean = false,
        retry: Boolean = false
    ) {

        DebugLog.e("Animals", "API Error", error)

        val failure: (Throwable) -> Unit = {
            if (retry) handleApiRequestFailureRetry(it, action, handleRequestError)
            else handleApiRequestFailure(it, handleRequestError)
        }

        if (error is HttpException && error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            viewModelScope.launch {
                runCatching {
                    withContext(Dispatchers.IO) {

                    }
                }.onSuccess {
                    action()
                }.onFailure(failure)
            }
        } else {
            failure(error)
        }
    }

    fun handleApiRequestFailure(
        error: Throwable,
        handleResponseFail: Boolean
    ) {
        DebugLog.e("Animals", "API Error", error)
        hideProgressBar()
        when (error) {
            is NoInternetException -> showNetworkError()
            is HttpException -> showAPIErrorDialog(
                error,
                handleRequestError = handleResponseFail
            )
            else -> showServerError()
        }
    }

    private fun handleApiRequestFailureRetry(
        error: Throwable,
        retryAction: () -> Unit,
        handleResponseFail: Boolean
    ) {
        hideProgressBar()
        when (error) {
            is NoInternetException -> showNetworkError(R.string.retry, retryAction, true)
            is HttpException -> showAPIErrorDialog(
                error,
                R.string.retry,
                retryAction,
                handleResponseFail,
                true
            )
            else -> showServerError(R.string.retry, retryAction, true)
        }

    }

    protected open fun showAPIErrorDialog(
        error: HttpException,
        okResId: Int? = null,
        action: (() -> Unit)? = null,
        handleRequestError: Boolean = false,
        retry: Boolean = false
    ) {

        when {
            error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED -> showToast("Unauthorized")


            handleRequestError ->

                error.response()?.errorBody().apply {
                    showToast("Api Error")
                }

            else -> showServerError(okResId, action, retry)
        }
    }

    protected fun <T> runActionWithProgress(
        action: suspend () -> T,
        success: ((T) -> Unit)? = null,
        failure: (Throwable) -> Unit = { handleApiRequestFailure(it, true) }
    ) {
        actionWithProgress(action, success, failure)()
    }

    protected fun <T> actionWithProgress(
        action: suspend () -> T,
        success: ((T) -> Unit)? = null,
        failure: (Throwable) -> Unit = { handleApiRequestFailure(it, true) }
    ): () -> Unit = {
        viewModelScope.launch {
            showBlockingProgressBar()
            runCatching {
                withContext(Dispatchers.IO) {
                    action()
                }
            }.onSuccess {
                hideProgressBar()
                success?.invoke(it)
            }.onFailure(failure)
        }
    }


}