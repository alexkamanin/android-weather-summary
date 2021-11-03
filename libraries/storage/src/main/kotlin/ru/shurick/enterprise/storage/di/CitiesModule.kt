package ru.shurick.enterprise.storage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.shurick.enterprise.storage.provider.CitiesProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CitiesModule {

    private val name = "cities"

    @Provides
    @Singleton
    fun provideCities(@ApplicationContext context: Context) = CitiesProvider(context.preferencesStore)

    private val Context.preferencesStore: DataStore<Preferences> by preferencesDataStore(name = name)
}