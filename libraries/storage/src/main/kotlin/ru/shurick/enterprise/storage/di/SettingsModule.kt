package ru.shurick.enterprise.storage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.shurick.enterprise.storage.SettingsStore
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.storage.serializer.SettingsSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    private val fileName = "settings.proto"

    @Provides
    @Singleton
    fun provideSettings(@ApplicationContext context: Context) = SettingsProvider(context.dataStore)

    private val Context.dataStore: DataStore<SettingsStore> by dataStore(
        fileName = fileName,
        serializer = SettingsSerializer
    )
}