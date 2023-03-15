package com.example.giphyexample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.giphyexample.R
import com.example.giphyexample.databinding.ItemGiphyBinding
import com.example.giphyexample.giphydata.GiphyPhoto

class GiphyAdapter(private val listener:OnItemClickListener):PagingDataAdapter<GiphyPhoto,GiphyAdapter.GiphyViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding=ItemGiphyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GiphyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val currentItem=getItem(position)
        if(currentItem!=null){
            holder.bind(currentItem)
        }


    }


    override fun getItemCount(): Int {
        return super.getItemCount()
    }







    inner class GiphyViewHolder(private val binding: ItemGiphyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val item=getItem(position)
                    if(item!=null){
                        listener.onItemClick(item)
                    }

                }
            }
        }

            fun bind(photo: GiphyPhoto){
                binding.apply {
                    Glide.with(itemView)
                        .asGif()
                        .load(photo.images.original.url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView)
                }
            }


    }

    interface OnItemClickListener{
        fun onItemClick(photo: GiphyPhoto)
    }


    companion object {
    private val COMPARATOR=object :DiffUtil.ItemCallback<GiphyPhoto>(){
        override fun areItemsTheSame(oldItem: GiphyPhoto, newItem: GiphyPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GiphyPhoto, newItem: GiphyPhoto): Boolean {
           return oldItem==newItem
        }

    }

    }


}