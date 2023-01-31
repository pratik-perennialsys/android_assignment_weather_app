package com.perennial.androidassignmentweatherapp.di.remote

import com.perennial.androidassignmentweatherapp.BuildConfig
import com.perennial.androidassignmentweatherapp.data.rest.ApiConstants
import com.perennial.androidassignmentweatherapp.data.rest.OkHttpInterceptor
import com.perennial.androidassignmentweatherapp.data.rest.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providerOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG)
                builder.addInterceptor(providerHttpsLoggingInterceptor())
            val interceptors = getInterceptors()
            interceptors.forEach { interceptor ->
                addInterceptor(interceptor)
            }
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun providerHttpsLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getInterceptors(): ArrayList<Interceptor> {
        return arrayListOf(OkHttpInterceptor())
    }
    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}