package com.example.mehrkalacoroutine.data.network

import com.example.mehrkalacoroutine.data.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class ApiOkHttpClient(
    private val tokenRepository: TokenRepository
){
    operator fun invoke(): OkHttpClient  {
        val requestInterceptor = Interceptor{chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("token" , tokenRepository.token())
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()

        return okHttpClient
    }
}