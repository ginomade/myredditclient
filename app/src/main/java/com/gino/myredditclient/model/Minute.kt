package com.gino.myredditclient.model

import java.util.concurrent.TimeUnit

inline class Minute(
        val value: Long
) {
    val timeUnit get() = TimeUnit.MINUTES
}