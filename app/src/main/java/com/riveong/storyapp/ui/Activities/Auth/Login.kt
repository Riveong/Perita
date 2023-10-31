package com.riveong.storyapp.ui.Activities.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.riveong.storyapp.Data.Preferences.LoginViewModel
import com.riveong.storyapp.Data.Preferences.SettingPreferences
import com.riveong.storyapp.Data.Preferences.ViewModelFactory
import com.riveong.storyapp.Data.Preferences.dataStore
import com.riveong.storyapp.Data.Retrofit.*
import com.riveong.storyapp.databinding.ActivityLoginBinding
import com.riveong.storyapp.ui.Activities.Welcoming.Splash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        enableButton()


        binding.loginInstead.setOnClickListener{
            val intent =  Intent(this, Register::class.java)
            startActivity(intent)



        }
        binding.edLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s != null) {
                    enableButton()
                }else{
                    disableButton()
                }

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s != null) {
                    enableButton()
                }else{
                    disableButton()
                }
            }
            override fun afterTextChanged(s: Editable) {
                if (s != null) {
                    enableButton()
                }else{

                    disableButton()
                }

            }
        })

        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.length>8) {
                    enableButton()
                }else{
                    disableButton()
                }

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length>8) {
                    enableButton()
                }else{

                    disableButton()
                }
            }
            override fun afterTextChanged(s: Editable) {
                if (s.length>8) {
                    enableButton()
                }else{

                    disableButton()
                }

            }
        })

        binding.button.setOnClickListener {view ->
            postLogins(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)


        }

    }


    private fun enableButton() {
        var emailField = binding.edLoginEmail.text
        var passwordField = binding.edLoginPassword.text
        binding.button.isEnabled = passwordField != null && passwordField.toString().isNotEmpty() && emailField != null && emailField.toString().isNotEmpty()
    }
    private fun disableButton() {
        binding.button.isEnabled = false
    }


//post
    private fun postLogins(email: String, password: String) {
        binding.progress.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
        val client = ApiConfig.getApiService("").postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    //logic if successful


                    if(responseBody.error != true){
                        val token = responseBody.loginResult?.token
                        val username = responseBody.loginResult?.name
                        val pref = SettingPreferences.getInstance(application.dataStore)
                        val loginViewModel = ViewModelProvider(this@Login, ViewModelFactory(pref)).get(
                            LoginViewModel::class.java
                        )
                        loginViewModel.saveThemeSetting(true)
                        loginViewModel.saveUserData(token!!,username!!)

                        Toast.makeText(this@Login, "Welcome back ${username}! ヾ(≧▽≦*)o}", Toast.LENGTH_SHORT).show()
                        intent = Intent(this@Login, Splash::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }




                } else {

                    binding.progress.visibility = View.INVISIBLE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@Login,"Not valid ￣へ￣" , Toast.LENGTH_SHORT).show()
                }
            }






            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {


            }
        })
    }
}
