package com.riveong.storyapp.ui.Activities.Welcoming

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.riveong.storyapp.R
import com.riveong.storyapp.databinding.ActivityOnBoardingBinding
import com.riveong.storyapp.ui.Activities.Auth.Login

class onBoarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        binding.button2.setOnClickListener {
            intent = Intent(this@onBoarding, Login::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }



    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.tile, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 1000
        }.start()
        ObjectAnimator.ofFloat(binding.ready, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 1000
        }.start()
        ObjectAnimator.ofFloat(binding.button2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 1000
        }.start()
    }
}