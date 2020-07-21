package com.rohitTheBest.owlbotdictionaryretrofit.module

import com.rohitTheBest.owlbotdictionaryretrofit.data.api.ApiHelper
import com.rohitTheBest.owlbotdictionaryretrofit.data.api.ApiHelperImp
import com.rohitTheBest.owlbotdictionaryretrofit.data.api.ApiService
import com.rohitTheBest.owlbotdictionaryretrofit.other.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DictionaryModule {

    val logging = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun providesOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImp): ApiHelper = apiHelper

}