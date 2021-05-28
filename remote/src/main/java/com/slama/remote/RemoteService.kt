package com.slama.remote

import com.slama.remote.data.remote.RemoteCollection
import com.slama.remote.data.remote.RemoteMovieDetail
import com.slama.remote.data.remote.RemoteNowPlayingMovies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal object RemoteService {

    interface Endpoints {

        @GET("movie/now_playing")
        suspend fun getListOfPlayingMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int
        ): RemoteNowPlayingMovies


        @GET("movie/{movie_id}")
        suspend fun getMovieDetail(
            @Path("movie_id") movieId: Long,
            @Query("api_key") apiKey: String
        ): RemoteMovieDetail

        @GET("collection/{collection_id}")
        suspend fun getCollection(
            @Path("collection_id") collectionId: Long,
            @Query("api_key") apiKey: String
        ): RemoteCollection
    }

    private const val BASE_API_URL = "https://api.themoviedb.org/3/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(getHttpClient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service by lazy<Endpoints> {
        retrofit.create(Endpoints::class.java)
    }

    fun endpoints(): Endpoints = service

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestWithUserAgent = chain.request().newBuilder()
                    .build()
                chain.proceed(requestWithUserAgent)
            }
            .addInterceptor(loggingInterceptor())
            .build()

    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }

        } else {
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BASIC)
                }
        }
    }
}