package com.gino.myredditclient.viewmodel

import com.gino.myredditclient.arch.ArchViewModel
import com.gino.myredditclient.repository.Repository

class FeedViewModel : ArchViewModel() {

    val posts = Repository.getPostsObservable()

    init {
        getPosts()
    }

    fun getPosts() = withLoadingAndError {
        Repository.updatePosts()
    }
}