package com.example.artabookadvancelevel.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.artabookadvancelevel.adapter.ArtRecyclerAdapter
import com.example.artabookadvancelevel.adapter.ImageRecylerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager  //RequestManager glide oz sinifidir
    ,private val artRecyclerAdapter:ArtRecyclerAdapter,
    private val imageRecylerAdapter: ImageRecylerAdapter
) :FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ImageApiFragment::class.java.name->ImageApiFragment(imageRecylerAdapter)
            ArtFragment::class.java.name->ArtFragment(artRecyclerAdapter) //ArtFragmentle is gorulerse
        ArtDetailFragment::class.java.name->ArtDetailFragment(glide)
            else->super.instantiate(classLoader, className)
        }




    }
}