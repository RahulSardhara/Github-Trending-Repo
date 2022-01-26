package com.graphybyte.githubtrendingrepo.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.graphybyte.githubtrendingrepo.db.AppDatabase
import com.graphybyte.githubtrendingrepo.network.NetworkInterceptor
import com.graphybyte.githubtrendingrepo.network.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    @UserBaseUrl
    fun providesUserBaseUrl() ="https://api.github.com/"


    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun providesDataRequestInterceptor() = HttpLoggingInterceptor { message ->
        Timber.i("------------API------------ $message")
    }.apply { level = HttpLoggingInterceptor.Level.BODY }


    @Singleton
    @Provides
    fun providesConverterFactory(gson: Gson?): Converter.Factory = GsonConverterFactory.create(gson ?: Gson())


    @Singleton
    @Provides
    fun providesSupportClient(
        networkInterceptor: NetworkInterceptor,
        dataRequestInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient()
        .newBuilder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(dataRequestInterceptor)
        .connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    @UserApiRetrofitInstance
    fun providesUserApiBuilder(
        @UserBaseUrl
        baseUrl: String,
        supportClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .client(supportClient)
        .build()


    @Singleton
    @Provides
    fun providesUserApiInstance(@UserApiRetrofitInstance networkBuilder: Retrofit): GithubApi =
        networkBuilder.create(GithubApi::class.java)

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application) = AppDatabase.getDatabase(context = application)


}