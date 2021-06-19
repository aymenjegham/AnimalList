package com.example.animallist.di.module

import android.content.Context
import com.example.animallist.data.datasource.api.EndpointInterceptor
import com.example.animallist.di.qualifier.PicassoHttpClient
import com.example.animallist.global.helpers.DebugLog
import com.example.animallist.global.utils.CONNECT_TIMEOUT
import com.example.animallist.global.utils.OKHTTP_CACHE_FILE_NAME
import com.example.animallist.global.utils.READ_TIMEOUT
import com.example.animallist.global.utils.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(object :
        HttpLoggingInterceptor.Logger {
        override fun log(message: String) {

            DebugLog.d("intercept_animals", message)
        }
    }).run {
        level = HttpLoggingInterceptor.Level.BODY
        this
    }

    @Provides
    @Singleton
    fun providesOkHTTPClient(
        loggingInterceptor: HttpLoggingInterceptor,
        endpointInterceptor: EndpointInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(endpointInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesCacheFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, OKHTTP_CACHE_FILE_NAME)


    @Provides
    @Singleton
    fun providesCache(cacheFile: File): Cache = Cache(cacheFile, 10L * 1000 * 1000)


    @Provides
    @Singleton
    @PicassoHttpClient
    fun providesPicassoOkHTTPClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: EndpointInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .cache(cache)
            .build()

}