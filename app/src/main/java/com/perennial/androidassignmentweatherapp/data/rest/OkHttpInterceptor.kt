package com.perennial.androidassignmentweatherapp.data.rest

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OkHttpInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
            .method(request.method, request.body)
        return chain.proceed(builder.build())
    }
}