package com.rolemodel.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        afterCreation(savedInstanceState)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected abstract fun afterCreation(bundle: Bundle?)
}