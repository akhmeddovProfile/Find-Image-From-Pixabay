package com.example.artabookadvancelevel.repo

import androidx.lifecycle.LiveData
import com.example.artabookadvancelevel.api.RetrofitApi
import com.example.artabookadvancelevel.model.ImageResponse
import com.example.artabookadvancelevel.roomdb.Art
import com.example.artabookadvancelevel.roomdb.ArtDao
import com.example.artabookadvancelevel.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


//1.fake repositery olusduraraq testing ede bilerik artiq ArtRepositery yerine ArtRepositeryInterface vere bilerik
//2.AppModule de duzeltdiyimiz artDao ve retrofiti constri=uctor Injection ile ala bikeceyik
@ViewModelScoped
class ArtRepositery
@Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi)
    :ArtRepositeryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertAll(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteItem(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeAll()
    }

    override suspend fun searchImage(imageSearch: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageSearch)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                } ?:Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: java.lang.Exception) {
            Resource.error("No data", null)
        }
    }

}