package com.example.artabookadvancelevel.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.example.artabookadvancelevel.MainCoroutineRule
import com.example.artabookadvancelevel.getOrAwaitValueTest
import com.example.artabookadvancelevel.repo.FakeArtRepository
import com.example.artabookadvancelevel.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ArtViewModelTest {
    //In Android, the main thread is also known as the UI thread,
    // It is the thread responsible for handling all UI-related operations,
    // including updating the user interface, processing user input, and running animations.


    //biz burada demeliyik ki biz coroutine ve ya live data istemirik dum duz sadece run et
    //burada JUnity Rulelarini belirliyeceyik
    //bunlarin hec biri emulator calismayacaq ona gore ui thread yoxdur ona gore main coroutine isledirik
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    private lateinit var viewModel:ArtViewModel
    @Before
    fun setup(){
        //Test Doubles yeni test kopya
        viewModel= ArtViewModel(FakeArtRepository())

    }

    @Test
    fun `insert art without  artist name return error`(){
        viewModel.makeart("Test","","1200")
        val value=viewModel.insertartmessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without art name return error`(){
        viewModel.makeart("","Da Vinci","1200")
        val value=viewModel.insertartmessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without year return error`(){
        viewModel.makeart("Mona Lisa","Da Vinci","")
        val value=viewModel.insertartmessage.getOrAwaitValueTest() //burada value baxariqsa LiveData oldugunu gorerik Live Data ASYNC isleyir buda problem olar bize mainthread calisan lazimdir
        //solve
        //1.Live data normal bir dataya cevirmek lazimdir
        //2.Bu test demeliyik ki ne olsada biz mainthread de calisdirmaq isteyoirik

        assertThat(value.status).isEqualTo(Status.ERROR)

    }
}