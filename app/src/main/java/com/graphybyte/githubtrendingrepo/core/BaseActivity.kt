package com.graphybyte.githubtrendingrepo.core

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.graphybyte.githubtrendingrepo.utils.errorSnackBar
import com.graphybyte.githubtrendingrepo.utils.successSnackBar
import com.graphybyte.githubtrendingrepo.BR


/**
 * All activities in the app should be inherited from this file.
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), View.OnClickListener {

    /**
     *  Can initialize/declare anything here which is to used across all the child activities
     */
    lateinit var binding: T

    abstract fun setActivityView(): Int

    abstract fun init()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityView().let {
            binding = DataBindingUtil.setContentView(this, it)
            binding.setVariable(BR.click, this)
        }
        init()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 0.9f
        applyOverrideConfiguration(newOverride)
    }

    fun errorToast(message: String, callback: ((Boolean) -> Unit)? = null) {
       errorSnackBar(message, callback)
    }

    fun successToast(message: String, callback: ((Boolean) -> Unit)? = null) {
       successSnackBar(message, callback)
    }


}