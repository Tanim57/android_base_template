package com.tanim.androidbasetemplate.base

import com.tigerit.schoolattendance.utils.setLocale
import androidx.databinding.ViewDataBinding
import com.tanim.androidbasetemplate.base.BaseViewModel
import androidx.appcompat.app.AppCompatActivity
import com.tanim.androidbasetemplate.base.BaseContract
import com.tanim.androidbasetemplate.base.BaseFragment.FragmentCallback
import javax.inject.Inject
import com.google.android.material.snackbar.Snackbar
import android.app.ProgressDialog
import android.content.Context
import androidx.annotation.LayoutRes
import com.tanim.androidbasetemplate.utils.NetworkUtils
import android.os.Build
import android.content.pm.PackageManager
import android.graphics.Color
import com.bcsprostuti.tanim.bcsprostuti.managers.AppPreferenceManager
import com.tanim.androidbasetemplate.di.component.ActivityComponent
import com.tanim.androidbasetemplate.di.module.ActivityModule
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import com.tanim.androidbasetemplate.base.AppCrashHandler
import androidx.databinding.DataBindingUtil
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tanim.androidbasetemplate.BuildConfig
import com.tanim.androidbasetemplate.R

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseContract.View, FragmentCallback {

    @Inject
    protected lateinit var mViewModel: V

    private var snackbar: Snackbar? = null
    private var mProgressDialog: ProgressDialog? = null

    lateinit var viewDataBinding: T
        private set

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int
    abstract fun bindView()

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layout: Int


    override val isNetworkConnected
        get() = NetworkUtils.isNetworkConnectionAvailable()


    override fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun hideKeyBoard() {
        val view = currentFocus
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun attachBaseContext(newBase: Context) {
        val appPreferenceManager = AppPreferenceManager(newBase)
        super.attachBaseContext(setLocale(newBase, appPreferenceManager.getLanguage()))
    }

    private val buildComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .appComponent((application as App).appComponent)
            .activityModule(ActivityModule(this))
            .build()

    public override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
        bindView()
        performDataBinding()
        if (!BuildConfig.DEBUG) {
            Timber.i("########################### EXCEPTION ################################")
            Thread.setDefaultUncaughtExceptionHandler(AppCrashHandler(this))
        }
        //Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(this));
    }


    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layout)
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.executePendingBindings()
    }

    override fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.i("Grant results: $grantResults")
    }

    abstract fun performDependencyInjection(buildComponent: ActivityComponent)
    override fun showProgressbar() {
        hideProgressbar()
        if (!this.isFinishing) {
            mProgressDialog = ViewUtils.showLoadingDialog(this)
        }
    }

    override fun showProgressbar(message: String) {
        hideProgressbar()
        if (!this.isFinishing) {
            mProgressDialog = ViewUtils.showLoadingDialog(this, message)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun hideProgressbar() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
            mProgressDialog!!.dismiss()
        }
    }

    override fun showProgressDialog() {
        hideProgressDialog()
        if (!this.isFinishing) {
            mProgressDialog = ViewUtils.showLoadingDialog(this)
        }
    }

    override fun showProgressDialog(message: String) {
        hideProgressDialog()
        if (!this.isFinishing) {
            mProgressDialog = ViewUtils.showLoadingDialog(this, message)
        }
    }

    override fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
            mProgressDialog!!.dismiss()
        }
    }

    override fun showSwipeRefreshLayout() {}
    override fun hideSwipeRefreshLayout() {}
    override fun enableAllView() {
        window.decorView.rootView.isEnabled = true
    }

    override fun disableAllView() {
        //Utility.enableDisableViewGroup((ViewGroup) getWindow().getDecorView().getRootView(),false);
        window.decorView.rootView.isEnabled = false
    }

    override fun showSnackBar(message: String) {
        if (snackbar != null && snackbar!!.isShown) {
            hideSnackBar()
        }
        snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val view = snackbar!!.view
        view.setBackgroundColor(Color.RED)
        snackbar!!.setActionTextColor(Color.WHITE)
        snackbar!!.show()
    }

    override fun showSnackBarError(message: String) {
        if (snackbar != null && snackbar!!.isShown) {
            hideSnackBar()
        }
        snackbar = Snackbar.make(findViewById(android.R.id.content), message, 15000)
        val view = snackbar!!.view
        view.setBackgroundColor(Color.RED)
        snackbar!!.setActionTextColor(Color.WHITE)
        snackbar!!.setAction("Dismiss") { v: View? -> snackbar!!.dismiss() }
        snackbar!!.show()
    }

    override fun showSnackBar(stringId: Int) {
        showSnackBar(getString(stringId))
    }

    override fun showSnackBarError(stringId: Int) {
        showSnackBarError(getString(stringId))
    }

    override fun hideSnackBar() {
        if (snackbar != null) {
            snackbar!!.dismiss()
        }
    }

    override fun showShortToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLongToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showShortToast(stringId: Int) {
        Toast.makeText(applicationContext, stringId, Toast.LENGTH_SHORT).show()
    }

    override fun showLongToast(stringId: Int) {
        Toast.makeText(applicationContext, stringId, Toast.LENGTH_LONG).show()
    }


    override fun finishView() {
        finish()
    }

    override fun onFragmentAttached() {}

    override fun onFragmentDetached(tag: String) {}

    private var alertDialog: AlertDialog? = null
    override fun showError(message: String) {
        if (alertDialog != null) {
            alertDialog!!.cancel()
            alertDialog = null
        }
        alertDialog = MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_error_outline_24)
            .setTitle(R.string.error)
            .setPositiveButton(R.string.close) { dialogInterface, i -> dialogInterface.dismiss() }
            .create()
        if (!this.isFinishing) {
            alertDialog!!.show()
        }
    }

    override fun showErrorInDetail(message: String, detail: String) {
        if (alertDialog != null) {
            alertDialog!!.cancel()
            alertDialog = null
        }
        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(message)
            .setMessage(detail)
            .setPositiveButton(R.string.close) { dialogInterface, i -> dialogInterface.dismiss() }
            .create()
        if (!this.isFinishing) {
            alertDialog!!.show()
        }
    }

    override fun hideError() {
        if (alertDialog != null) {
            alertDialog!!.cancel()
            alertDialog = null
        }
    }
}