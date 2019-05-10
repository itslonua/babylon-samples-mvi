package com.babylone.playbook.data.network


import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class NetworkTransportInterceptorsFactory {

    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun responseInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder().build()

            return@Interceptor try {
                chain.proceed(request)
            } catch (e: Exception) {
                throw RetrofitNetworkException()
            }
        }
    }
}