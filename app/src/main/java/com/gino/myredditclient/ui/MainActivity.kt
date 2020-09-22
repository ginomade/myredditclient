package com.gino.myredditclient.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gino.myredditclient.R
import com.gino.myredditclient.ui.feed.FeedFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.isEmpty()) {
            changeFragment(FeedFragment(), false)
        }
    }

    private fun changeFragment(fr: Fragment, addToBackStack: Boolean) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fr, fr.javaClass.name)
                .run {
                    if (addToBackStack) {
                        addToBackStack(fr.javaClass.name)
                    } else {
                        this
                    }
                }
                .commit()
    }
}
