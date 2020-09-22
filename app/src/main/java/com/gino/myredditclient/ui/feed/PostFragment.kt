package com.gino.myredditclient.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.gino.myredditclient.App.Companion.picassoInstance
import com.gino.myredditclient.R
import com.gino.myredditclient.arch.BaseFragment
import com.gino.myredditclient.model.Entry
import com.gino.myredditclient.util.asDateTimeString
import com.gino.myredditclient.util.fromHtml
import com.gino.myredditclient.util.into
import com.gino.myredditclient.util.openURL
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.item_post_header.*

class PostFragment : BaseFragment() {

    override val layoutRes get() = R.layout.fragment_post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val post = arguments!!.getSerializable(Entry::class.java.canonicalName) as Entry

        post.author?.let {
            author.text = it.name
            author.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            it.uri?.let { uri ->
                author.setOnClickListener { v -> v.context.openURL(uri) }
            }
        }

        post.title?.let {
            title.text = it
        }
        post.updated?.let {
            date.text = it.asDateTimeString
        }
        post.content?.let {
            content_view.text = it.fromHtml()
        }
        post.imgUri?.let {
            progress.visibility = View.VISIBLE
            picassoInstance.load(it).into(image) {
                progress.visibility = View.GONE
            }
        }

        post_header.transitionName = post.id.toString()
    }
}