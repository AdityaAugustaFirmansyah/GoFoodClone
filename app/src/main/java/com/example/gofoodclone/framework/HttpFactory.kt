package com.example.gofoodclone.framework

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object HttpFactory {
    fun createMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    fun createRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://foodmarket-api.aryaaditiya.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .callFactory(createOkHttpClient(createLoggingInterceptor()))
            .build()
    }

    fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                val req = it.request().newBuilder()
                    .addHeader("Accept","application/json")
                    .build()
                it.proceed(req)
            }
            .build()
    }

    fun createLoggingInterceptor():HttpLoggingInterceptor{

        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}