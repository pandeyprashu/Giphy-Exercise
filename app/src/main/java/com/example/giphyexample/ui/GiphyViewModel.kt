package com.example.giphyexample.ui

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.giphyexample.data.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GiphyViewModel@Inject constructor(
    private val repository: GiphyRepository) :ViewModel(){

    private val currentQuery= MutableLiveData(DEFAULT_QUERY)

        val gifs=currentQuery.switchMap {queryString->
            repository.getSearchResults(queryString).cachedIn(viewModelScope)

        }


    fun searchgifs(query:String){
        currentQuery.value=query
    }


   companion object{
       private const val DEFAULT_QUERY="Trending"
    }
}