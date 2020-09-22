package com.gino.myredditclient.repository.rest

import com.gino.myredditclient.model.Feed
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {
    companion object {
        private const val RSS_SUFFIX = ".rss"
    }

    @GET(RSS_SUFFIX)
    suspend fun getPostsFromMain(): Feed

    @GET("r/{subreddit}/$RSS_SUFFIX")
    suspend fun getPostsForSubreddit(
            @Path("subreddit") subreddit: String
    ): Feed
}