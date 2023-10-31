package com.riveong.storyapp.ui.Activities.Welcoming

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.riveong.storyapp.ui.Activities.Auth.Login
import com.riveong.storyapp.Data.Preferences.LoginViewModel
import com.riveong.storyapp.Data.Preferences.SettingPreferences
import com.riveong.storyapp.Data.Preferences.ViewModelFactory
import com.riveong.storyapp.R
import com.riveong.storyapp.ui.Activities.MainActivity

class Splash : AppCompatActivity() {
    private val Context.dataStore by preferencesDataStore("app_preferences")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({
            wasLoginned()
        }, 2000)

}


        private fun wasLoginned() {
            val pref = SettingPreferences.getInstance(application.dataStore)
            val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
                LoginViewModel::class.java
            )
            loginViewModel.apply {
                loginViewModel.getLoginned().observe(this@Splash) { isLoggined: Boolean ->
                    loginViewModel.getJwtToken().observe(this@Splash) { theToken: String ->
                        loginViewModel.getUserName().observe(this@Splash) { theName: String ->

                            if (!isLoggined) {
                                startActivity(Intent(this@Splash, onBoarding::class.java))
                                finishAffinity()

                            } else {
                                startActivity(Intent(this@Splash, MainActivity::class.java))
                                finishAffinity()
                            }


                        }
                    }
                }
            }

        }
    }
