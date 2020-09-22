package com.gino.myredditclient.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gino.myredditclient.util.inflateView

abstract class BaseFragment : Fragment() {
    @get: LayoutRes
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            container?.inflateView(layoutRes)
}