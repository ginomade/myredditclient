package com.gino.myredditclient.repository.sharedPreferences

import android.content.SharedPreferences

interface PreferencesApi {
    val subreddit: String

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}