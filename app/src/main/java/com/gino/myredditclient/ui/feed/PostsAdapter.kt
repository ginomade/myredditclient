package com.gino.myredditclient.ui.feed

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gino.myredditclient.App.Companion.picassoInstance
import com.gino.myredditclient.R
import com.gino.myredditclient.model.Entry
import com.gino.myredditclient.util.asDateTimeString
import com.gino.myredditclient.util.inflateView
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.item_post_header.view.*

class PostsAdapter(
        private val onClickListener: (Entry, View) -> Unit
) : ListAdapter<Entry, PostsAdapter.PostViewHolder>(PostsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PostViewHolder(parent.inflateView(R.layout.item_post))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
            holder.bind(getItem(position))

    inner class PostViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val date = v.date
        private val author = v.author
        private val title = v.title
        private val image = v.image
        private val postHeader = v.post_header

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) = onClickListener(getItem(adapterPosition), postHeader)

        fun bind(post: Entry) {
            date.text = post.updated?.asDateTimeString
            author.text = post.author?.name
            title.text = post.title
            picassoInstance.load(post.imgUri).fit().centerCrop().placeholder(R.drawable.ic_image_black).into(image)

            postHeader.transitionName = post.id.toString()
        }
    }

    private class PostsDiffUtil : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry) = oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry) = oldItem == newItem
    }
}