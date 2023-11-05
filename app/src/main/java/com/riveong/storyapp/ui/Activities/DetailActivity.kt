package com.riveong.storyapp.ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.riveong.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDetailName.text = intent.getStringExtra(EXTRA_USERNAME)
        binding.tvDetailDescription.text = intent.getStringExtra(EXTRA_DESCRIPTION)
        Glide.with(this@DetailActivity)
            .load(intent.getStringExtra(EXTRA_IMAGE))
            .into(binding.ivDetailPhoto)





    }
}