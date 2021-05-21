package com.slama.remote

import com.slama.remote.data.remote.RemoteNowPlayingMovies
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

internal object RemoteService {

    interface Endpoints {

        @GET("/movie/now_playing")
        fun getNowPlayingMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): Observable<RemoteNowPlayingMovies>
    }

    private const val BASE_API_URL = "https://api.themoviedb.org/3"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(getHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service by lazy<Endpoints> {
        retrofit.create(Endpoints::class.java)
    }

    fun getRemoteEndpoints(): Endpoints = service

    private fun getHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder()
                .build()
            chain.proceed(requestWithUserAgent)
        }
        return okHttpBuilder.build()
    }
}