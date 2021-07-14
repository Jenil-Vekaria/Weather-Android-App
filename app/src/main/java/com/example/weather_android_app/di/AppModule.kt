package com.example.weather_android_app.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.weather_android_app.data.WeatherApi
import com.example.weather_android_app.di.moshiadapter.WeatherJsonAdapter
import com.example.weather_android_app.repository.DefaultRepository
import com.example.weather_android_app.repository.MainRepository
import com.example.weather_android_app.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    //Moshi
    @Provides
    fun provideMoshiBuilder() =
        Moshi.Builder()
            .add(WeatherJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

    //Reference for OpenWeatherAPI
    @Singleton
    @Provides
    fun provideOpenWeatherAPI(moshi: Moshi): WeatherApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.OPEN_WEATHER_API)
            .build()
            .create(WeatherApi::class.java)

    //Reference fro MetaWeatherAPI
    @Singleton
    @Provides
    fun provideMetaWeatherAPI(moshi: Moshi) =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.META_WEATHER_API)
            .build()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideDefaultRepository(api: WeatherApi): MainRepository = DefaultRepository(api)

}