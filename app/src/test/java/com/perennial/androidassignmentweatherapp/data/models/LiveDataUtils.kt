package com.perennial.androidassignmentweatherapp.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.await(
time: Long = 10,
timeUnit: TimeUnit = TimeUnit.SECONDS,
testObserve: () -> Unit = {}
) : T {
    var data: T? = null
    var latch = CountDownLatch(10)

    val observer = object: Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@await.removeObserver(this)
        }
    }
    this.observeForever(observer)

    testObserve.invoke()

    if(!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData never initialized")
    }

    return data as T
}