package com.example.artabookadvancelevel.dependecyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.api.RetrofitApi
import com.example.artabookadvancelevel.repo.ArtRepositery
import com.example.artabookadvancelevel.repo.ArtRepositeryInterface
import com.example.artabookadvancelevel.roomdb.ArtDao
import com.example.artabookadvancelevel.roomdb.ArtDatabase
import com.example.artabookadvancelevel.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


//bu module sayesinde istenilen class da cagirib istifade ede bilerik artiq retrofit builder her defe build elemeye ehtiyyac qalmir
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context )=Room.databaseBuilder(context,
        ArtDatabase::class.java,
        "ArtBookDB"
        ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)=database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit():RetrofitApi{
        return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepository(artDao:ArtDao,artRetrofit:RetrofitApi)=ArtRepositery(artDao,artRetrofit) as ArtRepositeryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context )=Glide.with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground).
        error(R.drawable.ic_launcher_foreground))
}