package com.example.artabookadvancelevel.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.artabookadvancelevel.model.ImageResponse
import com.example.artabookadvancelevel.roomdb.Art
import com.example.artabookadvancelevel.util.Resource

class FakeArtRepositoryTest:ArtRepositeryInterface {
        //fake repository hec bir retrofit response almadan fake sekilde TDD mentiqine uygun edirik hec bir sey bu class da heqiqiqi deyil sadece test ucundur
        //misal olaraq esl repositoryde dao ve retrofiti add eleyerken biz burda fake ni duzeltmis oluruq testing ucun
    private val arts= mutableListOf<Art>()
    //deyisdirile biler bir list
    private val liveData=MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
        }

    override fun getArt(): LiveData<List<Art>> {
        return liveData
    }

    override suspend fun searchImage(imageSearch: String): Resource<ImageResponse> {
//her hansisa search etmeyeceyik ona gore succes dondureceyik
        return Resource.success(ImageResponse(listOf(),0,0))
    }
    //elave etdiyimiz arts livedata elave olunmasina emin olmaq ucun

    private fun refreshData(){
        liveData.postValue(arts)
    }
}