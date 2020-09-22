package com.gino.myredditclient.arch

import androidx.lifecycle.MutableLiveData

class DefaultedLiveData<T : Any>(defaultValue: T) : MutableLiveData<T>() {

    init {
        value = defaultValue
    }

    override fun getValue() = super.getValue()!!
}