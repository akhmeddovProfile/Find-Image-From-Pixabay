package com.example.artabookadvancelevel.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.artabookadvancelevel.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


//burada test etmirik testlerde istifade olunan modullari yaziriq

@InstallIn(SingletonComponent::class)
@Module

object TestAppModule {
    @Provides
    @Named("testDatabase")
    fun injectMemoryRoom(@ApplicationContext context: Context)=
         Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java
        ).allowMainThreadQueries().build()


}