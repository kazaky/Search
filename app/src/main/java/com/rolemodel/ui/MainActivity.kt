package com.rolemodel.ui

import android.os.Bundle
import com.rolemodel.R
import com.rolemodel.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun afterCreation(bundle: Bundle?) = Unit
}