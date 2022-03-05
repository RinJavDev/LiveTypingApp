package com.rinat.livetypingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rinat.livetypingapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)
    }
}