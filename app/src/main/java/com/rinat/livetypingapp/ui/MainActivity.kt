package com.rinat.livetypingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rinat.livetypingapp.R
import com.rinat.livetypingapp.router.MainRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var router: MainRouter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
        router.setActivity(this)
    }
}