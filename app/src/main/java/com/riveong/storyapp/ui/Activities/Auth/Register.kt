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
import com.riveong.storyapp.Data.Retrofit.ApiConfig
import com.riveong.storyapp.Data.Retrofit.LoginResponse
import com.riveong.storyapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        enableButton()


        binding.LoginInstead.setOnClickListener{
            val intent =  Intent(this, Login::class.java)
            startActivity(intent)



        }
        binding.edRegisterEmail.addTextChangedListener(object : TextWatcher {
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

            }
        })

        binding.edRegisterName.addTextChangedListener(object : TextWatcher {
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

            }
        })

        binding.edRegisterPassword.addTextChangedListener(object : TextWatcher {
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

            }
        })

        binding.button.setOnClickListener {view ->
            postLogins(binding.edRegisterEmail.text.toString(), binding.edRegisterPassword.text.toString(), binding.edRegisterName.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)


        }

    }


    private fun enableButton() {
        var emailField = binding.edRegisterEmail.text
        var passwordField = binding.edRegisterPassword.text
        var nameField = binding.edRegisterName.text
        binding.button.isEnabled = passwordField != null && passwordField.toString().isNotEmpty() && emailField != null && emailField.toString().isNotEmpty() && nameField != null && nameField.toString().isNotEmpty()
    }
    private fun disableButton() {
        binding.button.isEnabled = false
    }


    //post
    private fun postLogins(email: String, password: String, name:String) {
        binding.progressBar.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
        val client = ApiConfig.getApiService("").postRegister(email, password, name)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    //logic if successful


                    if(responseBody.error != true){

                        Toast.makeText(this@Register, "User created! Please login for the tales ヾ(≧▽≦*)o}", Toast.LENGTH_SHORT).show()
                        intent = Intent(this@Register, Login::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }




                } else {

                    binding.progressBar.visibility = View.INVISIBLE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    Toast.makeText(this@Register,"Not valid ￣へ￣" , Toast.LENGTH_SHORT).show()
                }
            }






            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {


            }
        })
    }
}
