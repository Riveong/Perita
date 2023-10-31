package com.riveong.storyapp.ui.Adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riveong.storyapp.Data.Repository.StoryEntity
import com.riveong.storyapp.databinding.CardBinding
import com.riveong.storyapp.ui.Activities.DetailActivity

class StoryAdapter :  ListAdapter<StoryEntity, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: StoryEntity) {
            binding.tvItemName.text = stories.title
            binding.idTVDescription.text = stories.Description
            binding.idTVName.text = stories.publishedAt
            Glide.with(itemView.context)
                .load(stories.urlToImage)
                .circleCrop()
                .into(binding.ivItemPhoto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stories = getItem(position)
        holder.bind(stories)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, stories.title)
            intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, stories.Description)
            intent.putExtra(DetailActivity.EXTRA_IMAGE, stories.urlToImage)
            holder.itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(holder.itemView.context as Activity).toBundle())
        }

    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
                override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}