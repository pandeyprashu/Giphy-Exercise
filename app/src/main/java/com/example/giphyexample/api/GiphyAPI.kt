package com.example.giphyexample.api

import android.os.Build.ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GiphyAPI {

    companion object{
        const val BASE_URL="https://api.giphy.com/v1/"
    }


    @GET("gifs/search?api_key=YOUR_API_KEY")
    suspend fun searchGif(@Query("q") query: String,
                          @Query("offset") offset: Int,
                          @Query("limit") limit:Int
    ) :GiphyResponse


}
