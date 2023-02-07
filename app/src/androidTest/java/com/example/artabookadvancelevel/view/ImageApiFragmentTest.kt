package com.example.artabookadvancelevel.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.adapter.ImageRecylerAdapter
import com.example.artabookadvancelevel.getOrAwaitValue
import com.example.artabookadvancelevel.launchFragmentInHiltContainer
import com.example.artabookadvancelevel.repo.FakeArtRepositoryTest
import com.example.artabookadvancelevel.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


//Bu class da biz fake url olusdurduq hansi ki ona click edirmi onu yoxladiq eger click etdise geri qayidirmi onu yoxlayiriq
@MediumTest
@HiltAndroidTest
@ExperimentalMultiplatform
class ImageApiFragmentTest {

    @get:Rule
    var hiltRule= HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()

    @Inject
    lateinit var  fragmentFactory:ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    //sekil secilib ya yox onu test edirik
    @Test
    fun testSelectImage(){
        //bunu edirik ki secim edilenden sonra geriye qayitmasi lazim
        val navControllor= Mockito.mock(NavHostController::class.java)
        val selectImageUrl="com.example.artabookadvancelevel.view" //biz burada bu deyeri yaradiriq cunki list ici bosdur
        val testViewModel=ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navControllor)
            imageapiviewmodel=testViewModel
            imageRecylerAdapter.images= listOf(selectImageUrl) //burada en azindan recyclerview icinde bir dene de data olacaq
        }

        Espresso.onView(withId(R.id.imageRecycler)).perform(
            //yeni haraya click edeceyini necenci image click etdiyini bildirirk
            RecyclerViewActions.actionOnItemAtPosition<ImageRecylerAdapter.ImageViewHolder>(
                0,click() //1.necenci position tiklayim, ve ne edim yeni (click et)
            )
        )
        Mockito.verify(navControllor).popBackStack()
        assertThat(testViewModel.selectedImage.getOrAwaitValue()).isNotEqualTo(selectImageUrl)

    }
}