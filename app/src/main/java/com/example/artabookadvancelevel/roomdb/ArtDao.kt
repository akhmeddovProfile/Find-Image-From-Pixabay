package com.example.artabookadvancelevel.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)//bununla biz bildiririk ki eyni id olarsa hemen id yerine yazacaq
    suspend fun insertAll(art:Art)

    @Delete
    suspend fun deleteItem(art: Art)

    @Query("SELECT * FROM arts")
     fun observeAll():LiveData<List<Art>>
}