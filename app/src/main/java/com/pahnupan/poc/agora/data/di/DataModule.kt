package com.pahnupan.poc.agora.data.di

import android.content.Context
import com.google.gson.Gson
import com.pahnupan.poc.agora.core.SharePref
import com.pahnupan.poc.agora.data.helper.HeaderInterceptor
import com.pahnupan.poc.agora.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideSharePref(
        @ApplicationContext context: Context
    ): SharePref {
        return SharePref(context)
    }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        sharePref: SharePref
    ): HeaderInterceptor {
        return HeaderInterceptor(sharePref)
    }

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "http://35.197.157.239:8080/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
        return okHttp.build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BASE_URL") baseUrl: String,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(baseUrl)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}