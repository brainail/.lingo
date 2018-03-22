package org.brainail.EverboxingLingo.data.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal class NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.urbandictionary.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /*
    @Provides
    fun provideUrbanApi(retrofit: Retrofit): UrbanApi = retrofit.create(UrbanApi::class.java)

    @Provides
    fun provideUrbanSuggestionsNetworkDataSource(urbanApi: UrbanApi): UrbanSuggestionsNetworkDataSource
            = UrbanSuggestionsNetworkDataSource(urbanApi)
    */
}