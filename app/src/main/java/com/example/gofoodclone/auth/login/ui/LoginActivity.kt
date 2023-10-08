package com.example.gofoodclone.auth.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gofoodclone.MyApplication
import com.example.gofoodclone.auth.register.ui.RegisterActivity
import com.example.domain.DataAuth
import com.example.domain.login.LoginPayload
import com.example.persentation.LoginViewModel
import com.example.persentation.LoginViewModelFactory
import com.example.gofoodclone.databinding.ActivityLoginBinding
import com.example.gofoodclone.dialog.DialogView
import com.example.gofoodclone.factories.LoginDecorator
import com.example.gofoodclone.factories.LoginServiceFactory
import com.example.gofoodclone.factories.RemoteLoginFactory
import com.example.gofoodclone.factories.SaveSessionFactory
import com.example.gofoodclone.factories.SessionDaoFactory
import com.example.gofoodclone.home.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: com.example.persentation.LoginViewModel
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val app = application as MyApplication
        viewModel = ViewModelProvider(
            this,
            com.example.persentation.LoginViewModelFactory(
                LoginDecorator.createFactory(
                    RemoteLoginFactory.createRemoteLoginFactory(
                        LoginServiceFactory.createLoginService()
                    ),
                    SaveSessionFactory.createSaveSession(
                        SessionDaoFactory.createSessionDaoFactory(app.tinyDB)
                    )
                )
            )
        )[com.example.persentation.LoginViewModel::class.java]
        viewModel.viewModelState.observe(this,
            object : BaseObserverLiveData<com.example.domain.DataAuth>(binding.loading) {
                override fun onSuccess(data: com.example.domain.DataAuth) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }

                override fun onFailure(msg: String) {
                    DialogView.createDialogError(msg, this@LoginActivity) {

                    }
                }

            })
        binding.button.setOnClickListener {
            val payloadAuth = com.example.domain.login.LoginPayload(
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