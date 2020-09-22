package com.gino.myredditclient.repository

import android.content.SharedPreferences
import com.gino.myredditclient.model.Minute
import com.gino.myredditclient.repository.database.DatabaseApi
import com.gino.myredditclient.repository.rest.RestApi
import com.gino.myredditclient.repository.sharedPreferences.PreferencesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Repository : SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var dbImpl: DatabaseApi
    private lateinit var restImpl: RestApi
    private lateinit var preferencesImpl: PreferencesApi

    fun init(dbImpl: DatabaseApi, restImpl: RestApi, prefImpl: PreferencesApi) {
        this.dbImpl = dbImpl
        this.restImpl = restImpl
        this.preferencesImpl = prefImpl

        this.preferencesImpl.registerListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                updatePosts()
            }
        }
    }

    suspend fun updatePosts() {
        val subreddit = preferencesImpl.subreddit

        val feedEntries = if (subreddit.isEmpty()) {
            restImpl.getPostsFromMain()
        } else {
            restImpl.getPostsForSubreddit(subreddit)
        }.entry ?: return

        dbImpl.setPosts(feedEntries).join()
    }

    fun getPostsObservable() = dbImpl.getPostsObservable()

}