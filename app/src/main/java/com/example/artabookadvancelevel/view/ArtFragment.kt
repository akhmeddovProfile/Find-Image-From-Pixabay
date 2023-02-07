package com.example.artabookadvancelevel.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.adapter.ArtRecyclerAdapter
import com.example.artabookadvancelevel.databinding.FragmentArtsBinding
import com.example.artabookadvancelevel.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment
     /*@Inject*/
    constructor(val artRecyclerAdapter: ArtRecyclerAdapter?=null)
    :Fragment(R.layout.fragment_arts) {
    private var fragmentArtsBinding:FragmentArtsBinding?=null
    lateinit var model: ArtViewModel

    //ekrani surusdururek delete isi
    private val swipeCallBack= object : ItemTouchHelper
    .SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }
        @SuppressLint("SuspiciousIndentation")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
         val layoutPosition=viewHolder.layoutPosition //burada position aliriq
         val selectedArt= artRecyclerAdapter?.arts?.get(layoutPosition) //burada secilmis image aliriq
            if (selectedArt != null) {
                model.deleteArt(selectedArt)
            } // burada silirik
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentArtsBinding.bind(view)
        fragmentArtsBinding=binding
        //modelimizi build edirik burada
        model=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }

        subscribedtoObserver()

        //recycler view baglanti yaradiriq
        binding.recyclerViewArts.adapter=artRecyclerAdapter
        binding.recyclerViewArts.layoutManager=LinearLayoutManager(requireContext())

        //
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArts)
    }



    //burada observerlere uzv olmagi bildireceyik
    fun subscribedtoObserver(){
        model.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter!!.arts=it //butun emeliyyatlari bir birine baglamis oluruq
        })
    }

    override fun onDestroy() {
        fragmentArtsBinding=null
        super.onDestroy()
    }

}