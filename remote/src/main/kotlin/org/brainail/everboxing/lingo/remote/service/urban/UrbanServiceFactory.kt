package org.brainail.everboxing.lingo.remote.service.urban

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.brainail.logger.L
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UrbanServiceFactory {

    fun makeUrbanService(isDebug: Boolean, vararg networkInterceptors: Interceptor): UrbanService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor(isDebug), networkInterceptors)
        return makeUrbanService(okHttpClient, makeGson())
    }

    private fun makeUrbanService(okHttpClient: OkHttpClient, gson: Gson): UrbanService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.urbandictionary.com")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(UrbanService::class.java)
    }

    private fun makeOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            networkInterceptors: Array<out Interceptor>): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            networkInterceptors.forEach { addNetworkInterceptor(it) }
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { L.d(it) }.apply {
            level = when {
                isDebug -> HttpLoggingInterceptor.Level.BASIC
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}