package com.example.gofoodclone.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gofoodclone.MyApplication
import com.example.gofoodclone.R
import com.example.gofoodclone.auth.session.usecase.SessionLocal
import com.example.gofoodclone.auth.login.ui.LoginActivity
import com.example.gofoodclone.auth.session.SessionDao
import com.example.gofoodclone.framework.TinyDB

class MainActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = application as MyApplication
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(SessionLocal(SessionDao(app.tinyDB)))
        )[HomeViewModel::class.java]
        homeViewModel.onCheckLogin.observe(this) {
            if (it.data == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}