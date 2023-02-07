package com.example.artabookadvancelevel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.roomdb.Art
import javax.inject.Inject

class ImageRecylerAdapter @Inject constructor(val glide : RequestManager):RecyclerView.Adapter<ImageRecylerAdapter.ImageViewHolder>() {
    class ImageViewHolder(item: View):RecyclerView.ViewHolder(item)

    //burada biz itemclick string edirik cunki url icine alsin  deyer string olaraq geri donus edecek
     private var onItemClickListener : ((String) -> Unit)? = null

    private val diffUtil=object: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem //eger bunlar beraber olsarsa true deyer donecek eger deyilse false
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }

    fun setonItemClickListener(listener: (String) -> Unit){
        onItemClickListener=listener
    }

    private val recylerDifferAdapter= AsyncListDiffer(this,diffUtil) // bununla biz diffutileden gelen deyeri asnc sekilde update olani getire bilerik

    var images:List<String>
        get() = recylerDifferAdapter.currentList
        set(value) = recylerDifferAdapter.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageview=holder.itemView.findViewById<ImageView>(R.id.singleArtImageView)
        val url=images[position]
        holder.itemView.apply {
            glide.load(url).into(imageview)
            //click edilen image view url bu sekilde ala bilirik
            imageview.setOnClickListener {
                onItemClickListener!!.let {clickforitemUrl->
                    clickforitemUrl(url)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}