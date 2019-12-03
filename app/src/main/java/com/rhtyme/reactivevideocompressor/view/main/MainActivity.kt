package com.rhtyme.reactivevideocompressor.view.main

import android.os.Bundle
import com.rhtyme.reactivevideocompressor.R
import com.rhtyme.reactivevideocompressor.view.base.BaseActivity
import com.rhtyme.reactivevideocompressor.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity() {

    val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}