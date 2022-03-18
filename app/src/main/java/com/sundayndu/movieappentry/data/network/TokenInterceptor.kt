package com.sundayndu.movieappentry.data.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("language", "en-US")
            .addQueryParameter("api_key", token)
            .build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}