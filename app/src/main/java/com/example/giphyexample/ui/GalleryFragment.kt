package com.example.giphyexample.ui

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.giphyexample.MainActivity
import com.example.giphyexample.R
import com.example.giphyexample.api.GiphyAPI
import com.example.giphyexample.data.GiphyPagingSource
import com.example.giphyexample.data.GiphyRepository
import com.example.giphyexample.databinding.FragmentGalleryBinding
import com.example.giphyexample.giphydata.GiphyPhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),GiphyAdapter.OnItemClickListener {
    private val viewModel by viewModels<GiphyViewModel>()

    private var _binding:FragmentGalleryBinding?=null
    private val binding get() = _binding!!
    private var count=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentGalleryBinding.bind(view)
        val adapter=GiphyAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator=null
            recyclerView.adapter=adapter.withLoadStateFooter(
                footer = GiphyLoadStateAdapter{adapter.retry()}
            )
            buttonRetry.setOnClickListener{
                adapter.retry()
            }
        }


        viewModel.gifs.observe(viewLifecycleOwner){pagingData->
            adapter.submitData(viewLifecycleOwner.lifecycle,pagingData)
            //Toast.makeText(activity,"ViewModel",Toast.LENGTH_SHORT).show()

       //Toast.makeText(activity,"${GiphyPagingSource.offsetExample}",Toast.LENGTH_SHORT).show()

        }



        adapter.addLoadStateListener {loadState->
         binding.apply {
             progressBar.isVisible=loadState.source.refresh is LoadState.Loading
             recyclerView.isVisible=loadState.source.refresh is LoadState.NotLoading
             buttonRetry.isVisible=loadState.source.refresh is LoadState.Error
             textViewError.isVisible=loadState.source.refresh is LoadState.Error

             Toast.makeText(activity,"ItemCount: ${adapter.itemCount}",Toast.LENGTH_SHORT).show()




             if(loadState.source.refresh is LoadState.NotLoading &&
                     loadState.append.endOfPaginationReached &&
                     adapter.itemCount<1){
                 recyclerView.isVisible=false
                 textViewEmpty.isVisible=true
                 Toast.makeText(activity," LoadStateNotLoading API Calling",Toast.LENGTH_SHORT).show()
             }else{
                 textViewEmpty.isVisible=false
             }

         }

        }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(photo: GiphyPhoto) {
        val action=GalleryFragmentDirections.actionGalleryFragment2ToDetailsFragment(photo)
        findNavController().navigate(action)
        Toast.makeText(activity," OnItem Click ",Toast.LENGTH_SHORT).show()

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery,menu)

        val searchItem=menu.findItem(R.id.action_search)
        val searchView=searchItem.actionView as SearchView


        searchView.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
             if(query!=null){
                 binding.recyclerView.scrollToPosition(0)
                 viewModel.searchgifs(query)
                 searchView.clearFocus()

             }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null


    }


}