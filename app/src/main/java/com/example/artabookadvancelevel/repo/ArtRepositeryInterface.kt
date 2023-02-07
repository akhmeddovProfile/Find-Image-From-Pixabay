package com.example.artabookadvancelevel.repo

import androidx.lifecycle.LiveData
import com.example.artabookadvancelevel.model.ImageResponse
import com.example.artabookadvancelevel.roomdb.Art
import com.example.artabookadvancelevel.util.Resource

 interface ArtRepositeryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

      fun getArt():LiveData<List<Art>>

    suspend fun searchImage(imageSearch:String):Resource<ImageResponse>

}