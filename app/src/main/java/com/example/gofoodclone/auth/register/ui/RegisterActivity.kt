package com.example.gofoodclone.auth.register.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.login.ui.BaseObserverLiveData
import com.example.gofoodclone.auth.register.CityDummy
import com.example.gofoodclone.auth.register.http.RegisterService
import com.example.gofoodclone.auth.register.http.usecase.RemoteRegister
import com.example.gofoodclone.auth.register.persentation.RegisterViewModel
import com.example.gofoodclone.auth.register.persentation.RegisterViewModelFactory
import com.example.gofoodclone.databinding.ActivityRegisterBinding
import com.example.gofoodclone.dialog.DialogView
import com.example.gofoodclone.framework.HttpFactory
import com.example.gofoodclone.framework.TinyDB
import com.example.gofoodclone.home.MainActivity
import retrofit2.create

class RegisterActivity : AppCompatActivity() {
    private var city: String = ""
    lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        val viewModel = ViewModelProvider(
            this, RegisterViewModelFactory(
                RemoteRegister(
                    HttpFactory.createRetrofit().create(RegisterService::class.java),
                    SaveSession(TinyDB(this))
                )
            )
        )[RegisterViewModel::class.java]
        viewModel.onPage.observe(this) {
            if (it == 1) {
                registerBinding.registerStep1.root.visibility = View.VISIBLE
                registerBinding.registerStep2.root.visibility = View.GONE
            } else {
                registerBinding.registerStep2.root.visibility = View.VISIBLE
                registerBinding.registerStep1.root.visibility = View.GONE
            }
        }

        viewModel.onRegister.observe(this,
            object : BaseObserverLiveData<DataAuth>(registerBinding.loading) {
                override fun onSuccess(data: DataAuth) {
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }

                override fun onFailure(msg: String) {
                    DialogView.createDialogError(msg, this@RegisterActivity) {

                    }
                }

            })
        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            CityDummy.cities()
        )
        registerBinding.registerStep2.spinner.adapter = arrayAdapter

        registerBinding.registerStep2.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                city = arrayAdapter.getItem(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        registerBinding.registerStep2.button3.setOnClickListener {
            viewModel.register(
                PayloadAuth(
                    name = registerBinding.registerStep1.editTextText.text.toString(),
                    email = registerBinding.registerStep1.editTextText2.text.toString(),
                    password = registerBinding.registerStep1.editTextTextPassword2.text.toString(),
                    password_confirmation = registerBinding.registerStep1.editTextTextPassword2.text.toString(),
                    phoneNumber = registerBinding.registerStep2.editTextText.toString(),
                    address = registerBinding.registerStep2.editTextText2.toString(),
                    houseNumber = registerBinding.registerStep2.editTextTextPassword2.toString(),
                    city = city,
                )
            )
        }
        registerBinding.registerStep1.button3.setOnClickListener {
            viewModel.onNextPage(
                PayloadAuth(
                    name = registerBinding.registerStep1.editTextText.text.toString(),
                    email = registerBinding.registerStep1.editTextText2.text.toString(),
                    password = registerBinding.registerStep1.editTextTextPassword2.text.toString()
                )
            )
        }
    }
}