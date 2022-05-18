package com.example.libraryhub.di

import com.example.libraryhub.api.*
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideAuthAPIInstance(BASE_URL: String) : AuthAPI =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.JWT}").build()
                chain.proceed(request)
            }.build())
            .build()
            .create(AuthAPI::class.java)

    @Provides
    @Singleton
    fun provideCategoryAPIInstance(BASE_URL: String) : CategoryAPI =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryAPI::class.java)

    @Provides
    @Singleton
    fun provideBookAPIInstance(BASE_URL: String) : BookAPI =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.JWT}").build()
                chain.proceed(request)
            }.build())
            .build()
            .create(BookAPI::class.java)

    @Provides
    @Singleton
    fun providePackageAPIInstance(BASE_URL: String) : PackageAPI =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.JWT}").build()
                chain.proceed(request)
            }.build())
            .build()
            .create(PackageAPI::class.java)

    @Provides
    @Singleton
    fun provideAdminAPIInstance(BASE_URL: String) : AdminAPI =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.JWT}").build()
                chain.proceed(request)
            }.build())
            .build()
            .create(AdminAPI::class.java)
}