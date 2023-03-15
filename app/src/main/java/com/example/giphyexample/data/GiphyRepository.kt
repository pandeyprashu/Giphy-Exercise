package com.example.giphyexample.data

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.giphyexample.api.GiphyAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(private val giphyAPI: GiphyAPI) {

    fun getSearchResults(query:String)=
        Pager(
           config = PagingConfig(
               pageSize = 10,
               maxSize = 100,
              enablePlaceholders = false
           ),
           pagingSourceFactory = {GiphyPagingSource(giphyAPI,query)}
        ).liveData


}