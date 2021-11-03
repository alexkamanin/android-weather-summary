package ru.shurick.enterprise.weather.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.shurick.enterprise.weather.data.api.WeatherApi
import ru.shurick.enterprise.weather.data.repository.WeatherRepositoryImpl
import ru.shurick.enterprise.weather.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WeatherModule {

    @Binds
    abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}