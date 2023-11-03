package com.riveong.storyapp.ui.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.riveong.storyapp.Data.Preferences.LoginViewModel
import com.riveong.storyapp.Data.Preferences.SettingPreferences
import com.riveong.storyapp.Data.Preferences.ViewModelFactory
import com.riveong.storyapp.databinding.ActivityMainBinding
import com.riveong.storyapp.ui.Activities.Welcoming.Splash
import com.riveong.storyapp.ui.Fragments.StoriesFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val Context.dataStore by preferencesDataStore("app_preferences")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        userNameGet()
        binding.addyourown.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStory::class.java))
        }
        binding.actionLogout.setOnClickListener {
            logOut()
        }
        binding.toMaps.setOnClickListener{
            startActivity(Intent(this@MainActivity,MapsActivity::class.java))
        }


    }

    private fun userNameGet() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LoginViewModel::class.java
        )
        loginViewModel.apply {

                    loginViewModel.getUserName().observe(this@MainActivity) { theName: String ->
                        binding.textView.text = "Welcome back,\n ${theName} :3"
                        val fragmentManager = supportFragmentManager
                        val fragment = StoriesFragment()
                        fragmentManager.beginTransaction()
                            .add(binding.fragmental.id, fragment)
                            .commit()




            }
        }
    }

    private fun logOut() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LoginViewModel::class.java
        )
        loginViewModel.apply {
            loginViewModel.delete()
            startActivity(Intent(this@MainActivity, Splash::class.java))
            finishAffinity()
        }
    }
}





