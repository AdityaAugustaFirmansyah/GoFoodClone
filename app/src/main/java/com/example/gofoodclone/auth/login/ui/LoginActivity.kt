package com.example.gofoodclone.auth.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gofoodclone.MyApplication
import com.example.gofoodclone.auth.register.ui.RegisterActivity
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.login.domain.LoginPayload
import com.example.gofoodclone.auth.login.http.LoginService
import com.example.gofoodclone.framework.HttpFactory
import com.example.gofoodclone.auth.login.http.usecase.RemoteLogin
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.login.persentation.LoginViewModel
import com.example.gofoodclone.auth.login.persentation.LoginViewModelFactory
import com.example.gofoodclone.auth.session.SessionDao
import com.example.gofoodclone.databinding.ActivityLoginBinding
import com.example.gofoodclone.dialog.DialogView
import com.example.gofoodclone.factories.LoginDecorator
import com.example.gofoodclone.factories.LoginServiceFactory
import com.example.gofoodclone.factories.RemoteLoginFactory
import com.example.gofoodclone.factories.SaveSessionFactory
import com.example.gofoodclone.factories.SessionDaoFactory
import com.example.gofoodclone.home.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val app = application as MyApplication
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(
                LoginDecorator.createFactory(
                    RemoteLoginFactory.createRemoteLoginFactory(
                        LoginServiceFactory.createLoginService()
                    ),
                    SaveSessionFactory.createSaveSession(
                        SessionDaoFactory.createSessionDaoFactory(app.tinyDB)
                    )
                )
            )
        )[LoginViewModel::class.java]
        viewModel.viewModelState.observe(this,
            object : BaseObserverLiveData<DataAuth>(binding.loading) {
                override fun onSuccess(data: DataAuth) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }

                override fun onFailure(msg: String) {
                    DialogView.createDialogError(msg, this@LoginActivity) {

                    }
                }

            })
        binding.button.setOnClickListener {
            val payloadAuth = LoginPayload(
                email = binding.editText.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
            viewModel.doLogin(payloadAuth)
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}