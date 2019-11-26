package com.rolemodel.koin.module

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.rolemodel.BuildConfig
import com.rolemodel.data.network.IdagioApi
import com.rolemodel.data.network.Urls.BASE_URL
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { NetworkManager(androidApplication()) }

    single<OkHttpClient> {
        val loggingInterceptor = LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build()

        val clientBuilder = OkHttpClient.Builder()

        // Add debug interceptors.
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        clientBuilder.build()
    }

    single<Retrofit> {
        val okHttpClient = get<OkHttpClient>()

        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    single<IdagioApi> {
        val retrofit = get<Retrofit>()
        retrofit.create(IdagioApi::class.java)
    }
}