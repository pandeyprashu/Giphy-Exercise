package com.example.giphyexample.di

import com.example.giphyexample.api.GiphyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    //GET NETWORK REQUEST
    fun provideRetrofit():Retrofit=
        Retrofit.Builder()
            .baseUrl(GiphyAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    //POST NETWORK REQUEST
    fun provideGiphyApi(retrofit: Retrofit):GiphyAPI=
       retrofit.create(GiphyAPI::class.java)

}