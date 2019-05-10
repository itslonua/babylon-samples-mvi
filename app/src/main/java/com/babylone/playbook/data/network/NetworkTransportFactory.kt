package com.babylone.playbook.data.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkTransportFactory {

    fun createOkHttpClient(interceptorFactory: NetworkTransportInterceptorsFactory): OkHttpClient {
        return with(OkHttpClient().newBuilder()) {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptorFactory.responseInterceptor())
            addInterceptor(interceptorFactory.loggingInterceptor())
        }.build()
    }

}
