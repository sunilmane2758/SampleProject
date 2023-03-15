package com.example.sampleproject.di

import android.provider.SyncStateContract
import com.example.sampleproject.retrofit.FakerApi
import com.example.sampleproject.utils.Constant
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModules {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesFakerAPI(retrofit: Retrofit): FakerApi {

        return retrofit.create(FakerApi::class.java)
    }
}