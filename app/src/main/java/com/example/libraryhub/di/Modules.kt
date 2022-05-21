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
    fun provideRetrofitBuilder(BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${AppPreferences.JWT}").build()
            chain.proceed(request)
        }.build())
        .build()

    @Provides
    @Singleton
    fun provideAuthAPIInstance(builder: Retrofit) : AuthAPI =
        builder.create(AuthAPI::class.java)

    @Provides
    @Singleton
    fun provideCategoryAPIInstance(builder: Retrofit) : CategoryAPI =
        builder.create(CategoryAPI::class.java)

    @Provides
    @Singleton
    fun provideBookAPIInstance(builder: Retrofit) : BookAPI =
        builder.create(BookAPI::class.java)

    @Provides
    @Singleton
    fun providePackageAPIInstance(builder: Retrofit) : PackageAPI =
        builder.create(PackageAPI::class.java)

    @Provides
    @Singleton
    fun provideAdminAPIInstance(builder: Retrofit) : AdminAPI =
        builder.create(AdminAPI::class.java)

    @Provides
    @Singleton
    fun provideNotificationAPIInstance(builder: Retrofit) : NotificationAPI =
        builder.create(NotificationAPI::class.java)
}