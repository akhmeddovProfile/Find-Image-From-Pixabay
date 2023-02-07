package com.example.artabookadvancelevel.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.databinding.FragmentArtsBinding
import com.example.artabookadvancelevel.databinding.FragmentsArtsDetailsBinding
import com.example.artabookadvancelevel.dependecyinjection.AppModule
import com.example.artabookadvancelevel.util.Status
import com.example.artabookadvancelevel.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ArtDetailFragment @Inject constructor(val glide: RequestManager):Fragment(R.layout.fragments_arts_details) {

    private var binding:FragmentsArtsDetailsBinding?=null

    /*lateinit var detailmodel:ArtViewModel*/
      var detailmodel: ArtViewModel?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artsDetailsBinding=FragmentsArtsDetailsBinding.bind(view)
        binding=artsDetailsBinding

        subscrebedToObserver()
        //modelimizi build edirik burada
        detailmodel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding?.saveButton?.setOnClickListener {
            detailmodel?.makeart(binding?.detailArtName?.text.toString(),binding?.detailArtistName?.text.toString()
                ,binding?.detailYear?.text.toString())
        }

        binding?.artImageView?.setOnClickListener {
            findNavController().navigate(ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
        }

        val callback=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()  //evvel ki UI gederek ordaki layout gosterri ve indikini destroy edir
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback) //bununla yaratdigimiz call back burada istifade edirik
    }


    fun subscrebedToObserver(){

        detailmodel?.selectedImageUrl?.observe(viewLifecycleOwner, Observer { url->
            println(url)
            binding?.let {
                glide.load(url).into(it.artImageView)
            }
        })
            //burada yoxlayacayiq success error loading oldugunu
        detailmodel?.insertartmessage?.observe(viewLifecycleOwner, Observer {itforstatus->

                when(itforstatus.status){
                    Status.SUCCESS->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        detailmodel?.resetInsertArtMsg()//bunu success den sonra error ve ya loading olmamasi ucundur
                    }
                    Status.ERROR->{
                    Toast.makeText(requireContext(),itforstatus.message?:"Error!",Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING->{

                    }
                }

        })
    }

    override fun onDestroy() {
        binding=null
        super.onDestroy()
    }
}