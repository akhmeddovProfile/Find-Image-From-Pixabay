package com.example.artabookadvancelevel.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artabookadvancelevel.model.ImageResponse
import com.example.artabookadvancelevel.repo.ArtRepositeryInterface
import com.example.artabookadvancelevel.roomdb.Art
import com.example.artabookadvancelevel.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import java.util.SplittableRandom
import javax.inject.Inject

//repositeryde duzeltdiyimiz ferqli fun istifade edeceyik
//Live datalari burada istifade edib View terefinde gostereceyik
@HiltViewModel
class ArtViewModel
@Inject
    constructor(
    private val repositeryInterface: ArtRepositeryInterface
    )
    :ViewModel() {

        //Art Fragment view  ucun istifade olunacaq
/*        val artlist=viewModelScope.launch {
            repositeryInterface.getArt()
        }*/

    val artList = repositeryInterface.getArt()

    //Image api Fragment
    private val images=MutableLiveData<Resource<ImageResponse>>()
    val imagelist:LiveData<Resource<ImageResponse>>//bu deyisdirile bilmez sadece kenardan yeni diger siniflerden bu deyeri ala bilerik
            get() = images   // bunun ucunde get method istifade olunur


    //burada sadece URL saxlayacayiq
    // MutableLive data sonradan deyisdirile bilen Live data demekdir
     val selectedImage=MutableLiveData<String>()
    val selectedImageUrl:LiveData<String>
       get() = selectedImage

    //Art Details fragmet hissesi
    //insertArtMsg bizim durumu follow edir yeni xeta olarsa ve ya empty bir hall olarsa qarsisina kecir
    private var insertArtMsg=MutableLiveData<Resource<Art>>()
    val insertartmessage:LiveData<Resource<Art>>
        get() = insertArtMsg

    //makeart fun yoxlamaq ucun istifade edirik k iuser ad yerine int bir deyer daxil edibmi
    fun makeart(name:String,artistName:String,year:String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
        insertArtMsg.postValue(Resource.error("Enter name,artis name,year",null))
        return
        }
        val yearInt=try {
            year.toInt()
        }catch (e:java.lang.Exception){
            insertArtMsg.postValue(Resource.error("Year should be number ",null))
            return
        }
        val art = Art(name,artistName,yearInt,selectedImage.value?:"")

        insertArt(art)
        setselectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }


            //ne succes ne error ne loading bunu etmesek byglar ola biler
    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
        }

    //istifadece bir sekil select etdiyi halda
    fun setselectedImage(url:String){
        selectedImage.postValue(url)//url view da alacayiq
    }

    fun deleteArt (art: Art) = viewModelScope.launch {
        repositeryInterface.deleteArt (art)
    }


    fun insertArt (art: Art) = viewModelScope.launch {
        repositeryInterface.insertArt (art)
    }

    fun searchForImage(searchString:String){
        if (searchString.isEmpty()) return
        //burada loading aliriq
        images.value=Resource.loading(null)
        viewModelScope.launch {
            //burada istek atiriq artiq
            val response =repositeryInterface.searchImage(searchString)
            images.value=response //burada images Resource oldugu ucun  val response: Resource<ImageResponse> etmek ucun elave nese etmeye ehtiyyac yoxdur


        }
    }

    }
