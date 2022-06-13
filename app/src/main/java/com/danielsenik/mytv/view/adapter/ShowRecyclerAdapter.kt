package com.danielsenik.mytv.view.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.danielsenik.mytv.databinding.ItemShowBinding
import com.danielsenik.mytv.model.Show
import com.danielsenik.mytv.view.fragment.ShowsFragmentDirections

class ShowRecyclerAdapter :
    ListAdapter<Show, ShowRecyclerAdapter.ShowViewHolder>(CarListComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            ItemShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class ShowViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            itemView.setOnClickListener { navigateToShow(show) }
            binding.apply {
                Glide.with(itemView.context)
                    .load(show.image.medium)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageIv)
                nameTv.text = show.name
                ratingRb.rating = show.rating.average / 2
                ratingTv.text = "(" + show.rating.average + ")"
                summaryTv.text = Html.fromHtml(show.summary)
            }
        }

        private fun navigateToShow(show: Show) {
            val direction = ShowsFragmentDirections.actionNavigationShowsToNavigationShow(show)
            itemView.findNavController().navigate(direction)
        }
    }

    class CarListComparator : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Show, newItem: Show) =
            oldItem == newItem
    }
}