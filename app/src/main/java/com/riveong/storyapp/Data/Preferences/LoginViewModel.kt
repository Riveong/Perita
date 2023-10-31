package com.riveong.storyapp.Data.Preferences



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getLoginned(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
    fun getJwtToken(): LiveData<String> = pref.getJwtToken().asLiveData()
    fun getUserName(): LiveData<String> = pref.getUsername().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
    fun saveUserData(theToken: String, theName: String) {
        viewModelScope.launch {
            pref.saveUserData(theToken,theName)
        }
    }
    fun delete(){
        viewModelScope.launch {
            pref.deleteAllPreferences()
        }

    }





}