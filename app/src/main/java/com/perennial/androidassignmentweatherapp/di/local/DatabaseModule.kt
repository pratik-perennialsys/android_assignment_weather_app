package com.perennial.androidassignmentweatherapp.di.local

import android.content.Context
import androidx.room.Room
import com.perennial.androidassignmentweatherapp.BuildConfig
import com.perennial.androidassignmentweatherapp.data.room.dao.LoginDao
import com.perennial.androidassignmentweatherapp.data.room.dao.SignupDao
import com.perennial.androidassignmentweatherapp.data.room.database.AppDatabase
import com.perennial.androidassignmentweatherapp.utils.RoomConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context): AppDatabase {
        val keyAlias: String = BuildConfig.SQL_SECRET_KEY
        val factory = SupportFactory(keyAlias.toByteArray())

        return Room.databaseBuilder(
            context, AppDatabase::class.java, RoomConstants.EN_DB_NAME.toString()
        ).openHelperFactory(factory).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSearchDAO(appDatabase: AppDatabase): SignupDao {
        return appDatabase.registerDatabaseDao
    }

    @Provides
    fun provideLoginDAO(appDatabase: AppDatabase): LoginDao {
        return appDatabase.loginDatabaseDao
    }
}