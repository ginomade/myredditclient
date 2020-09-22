package com.gino.myredditclient.repository.database

import com.gino.myredditclient.model.Entry
import com.gino.myredditclient.model.Entry_
import com.gino.myredditclient.model.MyObjectBox
import com.gino.myredditclient.util.replace
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ObjectBoxDB(baseDir: File) : DatabaseApi, CoroutineScope {

    override val coroutineContext = Dispatchers.IO

    private val cacheStore = MyObjectBox
            .builder()
            .baseDirectory(baseDir)
            .name("db_cache")
            .build()

    private val postsBox = cacheStore.boxFor(Entry::class.java)

    override fun setPosts(posts: List<Entry>) = launch {
        postsBox.replace(posts)
    }

    override fun getPostsObservable() = postsBox
            .query()
            .order(
                    Entry_.updated,
                    QueryBuilder.DESCENDING)
            .build()
            .let(::ObjectBoxLiveData)
}