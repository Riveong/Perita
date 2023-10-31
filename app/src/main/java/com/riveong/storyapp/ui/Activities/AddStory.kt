package com.riveong.storyapp.ui.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.riveong.storyapp.Data.Preferences.LoginViewModel
import com.riveong.storyapp.Data.Preferences.SettingPreferences
import com.riveong.storyapp.Data.Preferences.ViewModelFactory
import com.riveong.storyapp.Data.Preferences.dataStore
import com.riveong.storyapp.Data.Retrofit.ApiConfig
import com.riveong.storyapp.Data.Retrofit.CammieResponse
import com.riveong.storyapp.Data.Retrofit.LoginResponse
import com.riveong.storyapp.Data.utils.getImageUri
import com.riveong.storyapp.Data.utils.reduceFileImage
import com.riveong.storyapp.Data.utils.uriToFile
import com.riveong.storyapp.R


import com.riveong.storyapp.databinding.ActivityAddStoryBinding
import com.riveong.storyapp.ui.Activities.Welcoming.Splash
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStory : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }


        binding.Gallery.setOnClickListener { startGallery() }
        binding.cammie.setOnClickListener { startCamera() }
        binding.submit.setOnClickListener { uploadImage() }
        binding.Pic.setOnClickListener { startGallery() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this@AddStory,"No media selected o(TヘTo)",Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }


    private fun showImage() {
        currentImageUri?.let {
            //Log.d("Image URI", "showImage: $it")
            binding.Pic.setImageURI(it)
        }
    }

    private fun uploadImage() {
        binding.progress.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE )
        val pref = SettingPreferences.getInstance(application.dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LoginViewModel::class.java
        )
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.desc.text.toString()

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                loginViewModel.getJwtToken().observe(this@AddStory) {Token: String->


                    val apiService = ApiConfig.getApiService(Token)
                    val client = apiService.uploadImage(multipartBody, requestBody)

                    client.enqueue(object : Callback<CammieResponse> {
                        override fun onResponse(
                            call: Call<CammieResponse>,
                            response: Response<CammieResponse>
                        ) {
                            val responseBody = response.body()
                            if (response.isSuccessful && responseBody != null) {
                                binding.progress.visibility = View.INVISIBLE
                                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                                Toast.makeText(
                                    this@AddStory,
                                    "Your legacy has been added to the history (/≧▽≦)/",
                                    Toast.LENGTH_SHORT
                                ).show()

                                intent = Intent(this@AddStory, MainActivity::class.java)
                                startActivity(intent)
                                finishAffinity()


                            }
                        }

                        override fun onFailure(call: Call<CammieResponse>, t: Throwable) {
                            Toast.makeText(
                                this@AddStory,
                                "something went wrong :(",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.progress.visibility = View.INVISIBLE
                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        }


                    })
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}