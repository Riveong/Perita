package com.riveong.storyapp.ui.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riveong.storyapp.Data.Retrofit.ListStoryItem
import com.riveong.storyapp.databinding.CardBinding
import com.riveong.storyapp.ui.Activities.DetailActivity

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: CardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stories = getItem(position)

        if(stories != null) {
            holder.bind(stories, holder)
        }
    }

    class ViewHolder(var binding: CardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem, holder: ViewHolder) {
            binding.apply {
                tvItemName.text = stories.name
                idTVDescription.text = stories.description
                idTVName.text = stories.createdAt
                Glide.with(holder.itemView.context)
                    .load(stories.photoUrl)
                    .circleCrop()
                    .into(ivItemPhoto)



                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, stories.name)
                    intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, stories.description)
                    intent.putExtra(DetailActivity.EXTRA_IMAGE, stories.photoUrl)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivItemPhoto, "detailPhoto"),
                            Pair(tvItemName, "detailName")
                        )

                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}