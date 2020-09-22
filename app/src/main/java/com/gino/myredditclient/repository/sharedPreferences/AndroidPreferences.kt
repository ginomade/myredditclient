package com.gino.myredditclient.repository.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gino.myredditclient.util.get

class AndroidPreferences(context: Context) : PreferencesApi {
    private val settingsPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val SUBREDDIT_KEY = "SUBREDDIT_KEY"

    override val subreddit get() = settingsPreferences[SUBREDDIT_KEY, "news"]

    override fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
            settingsPreferences.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
            settingsPreferences.unregisterOnSharedPreferenceChangeListener(listener)
}