package com.example.giphyexample.giphydata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiphyPhoto(val id:String,
                      val title: String,
                      val images: GiphyImages
) :Parcelable{
    @Parcelize
    data class GiphyImages(val original: GiphyImage):Parcelable
    @Parcelize
    data class GiphyImage(val url: String):Parcelable

}
