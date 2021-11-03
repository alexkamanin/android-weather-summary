package ru.shurick.enterprise.city.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.shurick.enterprise.city.data.api.AutocompleteCityApi
import ru.shurick.enterprise.city.data.repository.AutoCompleteCityRepositoryImpl
import ru.shurick.enterprise.city.domain.repository.AutoCompleteCityRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AutocompleteCityApiModule {

    @Provides
    @Singleton
    fun provideAutocompleteCityApi(retrofit: Retrofit): AutocompleteCityApi = retrofit.create(
        AutocompleteCityApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
interface CityModule {

    @Binds
    abstract fun bindCityRepository(repository: AutoCompleteCityRepositoryImpl): AutoCompleteCityRepository
}