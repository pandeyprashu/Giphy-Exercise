package com.example.giphyexample.data

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphyexample.api.GiphyAPI
import com.example.giphyexample.giphydata.GiphyPhoto
import com.example.giphyexample.ui.GalleryFragment
import com.example.giphyexample.ui.GalleryFragmentDirections
import com.example.giphyexample.ui.details.DetailsFragmentArgs
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.coroutineContext


private const val GIPHY_STARTING_PAGE_INDEX=0
class GiphyPagingSource(private val giphyAPI: GiphyAPI, private val query:String):PagingSource<Int, GiphyPhoto>()
{
    var offsetExample=0
    var limitExample=0


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyPhoto> {
        val position=params.key ?: GIPHY_STARTING_PAGE_INDEX

        return try {
             val response = giphyAPI.searchGif(query, position, 10)
            val gifs= response.data

            offsetExample=position
            limitExample=params.loadSize
            Log.d(TAG,"OFFSET :${offsetExample}")
            Log.d(TAG,"LIMIT :${limitExample}")


            LoadResult.Page(
                data=gifs,
                prevKey = if(position== GIPHY_STARTING_PAGE_INDEX) null else position-1,
                nextKey = if(gifs.isEmpty()) null else position+params.loadSize
            )

        }catch(e:Exception){
            LoadResult.Error(e)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, GiphyPhoto>): Int? {
       return state.anchorPosition?.let{
           state.closestPageToPosition(it)?.prevKey?.plus(10)
               ?:state.closestPageToPosition(it)?.nextKey?.minus(10)
       }
    }

}
