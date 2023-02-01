package com.perennial.androidassignmentweatherapp.di.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.perennial.androidassignmentweatherapp.utils.SharedPrefConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.M)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            SharedPrefConstants.EN_PREF_FILE_NAME.toString(), // fileName
            masterKeyAlias, // masterKeyAlias
            context, // context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // prefKeyEncryptionScheme
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // prefvalueEncryptionScheme
        )
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper(@ApplicationContext context: Context): SharedPrefUtils {
        return SharedPrefUtils(provideSharedPreference(context))
    }
}