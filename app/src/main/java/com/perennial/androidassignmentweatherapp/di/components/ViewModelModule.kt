package com.perennial.androidassignmentweatherapp.di.components

import com.perennial.androidassignmentweatherapp.data.repo.implementation.LocationWeatherRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.repo.implementation.LoginRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.repo.implementation.SignupRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.LocationWeatherRepository
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.LoginRepository
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.SignupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    abstract fun bindSignupRepository(repository: SignupRepositoryImpl): SignupRepository

    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindLocationWeatherRepository(repository: LocationWeatherRepositoryImpl): LocationWeatherRepository
}