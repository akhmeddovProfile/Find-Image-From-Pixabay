package com.example.artabookadvancelevel.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.adapter.ImageRecylerAdapter
import com.example.artabookadvancelevel.databinding.FragmentImageApiBinding
import com.example.artabookadvancelevel.util.Status
import com.example.artabookadvancelevel.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment
@Inject
constructor( val imageRecylerAdapter: ImageRecylerAdapter)
    :Fragment(R.layout.fragment_image_api) {

    lateinit var imageapiviewmodel:ArtViewModel
    private var fragmentBinding:  FragmentImageApiBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageapiviewmodel= ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)

        subscribedToObserver()

        var job:Job?=null

        //istifadecinin her yazdigi soze uygun sekil axtarisi olacaq
        binding.searchText.addTextChangedListener {
        job?.cancel() //eger silindise job yeani search isini cancel edirik cunki bloklana biler yaz sil ederek
            job=lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        imageapiviewmodel.searchForImage(it.toString())
                    }
                }
            }
        }
        fragmentBinding = binding
        binding.imageRecycler.adapter = imageRecylerAdapter
                binding.imageRecycler.layoutManager = GridLayoutManager (requireContext(),3)//yan yana gostermek ucun


    imageRecylerAdapter.setonItemClickListener {
        findNavController().popBackStack()
        imageapiviewmodel.setselectedImage(it)
    }



    }

    fun subscribedToObserver(){
        imageapiviewmodel.imagelist.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                val urls=it.data?.hits?.map { imageResult ->
                    imageResult.previewURL
                }
                    imageRecylerAdapter.images=urls?: listOf()
                    fragmentBinding?.progressBar?.visibility=View.GONE
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message?:"Error!",Toast.LENGTH_SHORT).show()
                    fragmentBinding?.progressBar?.visibility=View.GONE
                }
                Status.LOADING->{
                    fragmentBinding?.progressBar?.visibility=View.VISIBLE
                }
            }
        })
    }
    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}