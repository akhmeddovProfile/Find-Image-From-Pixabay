package com.example.artabookadvancelevel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor
    (val glide :RequestManager) :RecyclerView.Adapter<ArtRecyclerAdapter.ArtviewHolder>()
{
    class ArtviewHolder(item:View):RecyclerView.ViewHolder(item)

    private val diffUtil=object:DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem //eger bunlar beraber olsarsa true deyer donecek eger deyilse false
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem==newItem
        }

    }

    private val recylerDifferAdapter=AsyncListDiffer(this,diffUtil) // bununla biz diffutileden gelen deyeri asnc sekilde update olani getire bilerik

    var arts:List<Art>
    get() = recylerDifferAdapter.currentList
    set(value) = recylerDifferAdapter.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtviewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtviewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtviewHolder, position: Int) {
        val imageview:ImageView=holder.itemView.findViewById(R.id.artRowImageView)
        val nameart:TextView=holder.itemView.findViewById(R.id.artName)
        val artistname:TextView=holder.itemView.findViewById(R.id.artistName)
        val artYear:TextView=holder.itemView.findViewById(R.id.artYear)
        val art=arts[position]
        holder.itemView.setOnClickListener {
             nameart.text="Art Name: ${art.name}"
            artistname.text="Artist Name: ${art.artistname}"
            artYear.text="Year: ${art.year}"
            glide.load(art.imageUrl).into(imageview)
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}