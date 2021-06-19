package com.example.animallist.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.animallist.ui.data.DialogAction
import com.example.animallist.ui.commondialog.progress.ProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null


    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>

    protected val picasso: Picasso
        get() = picassoLazy.get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
    }


    protected fun registerBaseObservers(viewModel: BaseViewModel) {
        registerProgressDialogObserver(viewModel)
        registerShowToastObserver(viewModel)
        registerSnackBarObserver(viewModel)

    }

    private fun registerProgressDialogObserver(viewModel: BaseViewModel) {
        viewModel.showProgressBar.observe(this, Observer(::showProgressDialog))
    }

    private fun registerShowToastObserver(viewModel: BaseViewModel) {
        viewModel.showToast.observe(this, Observer(::showToast))
    }

    private fun registerSnackBarObserver(viewModel: BaseViewModel) {
        viewModel.showSnackBar.observe(this, Observer(::showSnackBar))
    }



    fun showProgressDialog(action: DialogAction) {
        progressDialog = if (action == DialogAction.DISMISS) {
            progressDialog?.dismiss()
            null
        } else {
            ProgressDialog(this, action == DialogAction.SHOW)
                .apply { show() }
        }
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        startActivity(Intent(this, kClass.java))
        if (shouldFinish) finish()
    }

    fun navigateToActivity(intent: Intent, shouldFinish: Boolean = false) {
        startActivity(intent)
        if (shouldFinish) finish()

    }
}