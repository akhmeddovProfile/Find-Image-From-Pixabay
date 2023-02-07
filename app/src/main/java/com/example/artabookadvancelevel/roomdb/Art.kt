package com.example.artabookadvancelevel.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    val name :String,
    val artistname:String,
    val year:Int,
    var imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null
)