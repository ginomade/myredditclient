package com.gino.myredditclient.repository.database

import androidx.lifecycle.LiveData
import com.gino.myredditclient.model.Entry
import kotlinx.coroutines.Job

interface DatabaseApi {

    fun setPosts(posts: List<Entry>): Job

    fun getPostsObservable(): LiveData<List<Entry>>
}