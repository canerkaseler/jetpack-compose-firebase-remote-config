package com.canerkaseler.firebaseremoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseRemoteConfigDi {

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig
            .setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(2L)
                    .build(),
            )
        firebaseRemoteConfig.setDefaultsAsync(defaultValueMap)
        return firebaseRemoteConfig
    }

    @Provides
    fun provideFirebaseRemoteConfigProvider(
        firebaseRemoteConfig: FirebaseRemoteConfig
    ): FirebaseRemoteConfigProvider {
        return FirebaseRemoteConfigProvider(firebaseRemoteConfig)
    }
}