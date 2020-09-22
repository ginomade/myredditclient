package com.gino.myredditclient

import android.annotation.SuppressLint
import android.app.Application
import com.gino.myredditclient.repository.Repository
import com.gino.myredditclient.repository.database.ObjectBoxDB
import com.gino.myredditclient.repository.rest.NetworkBuilder
import com.gino.myredditclient.repository.sharedPreferences.AndroidPreferences
import com.squareup.picasso.Picasso

class App : Application() {

    val BASE_URL = "https://www.reddit.com/"

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var picassoInstance: Picasso
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Repository.init(
            ObjectBoxDB(cacheDir),
            NetworkBuilder.buildService(BASE_URL),
            AndroidPreferences(this)
        )

        picassoInstance = NetworkBuilder.buildPicasso(this)

    }
}