package com.example.artabookadvancelevel.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.artabookadvancelevel.R
import com.example.artabookadvancelevel.getOrAwaitValue
import com.example.artabookadvancelevel.launchFragmentInHiltContainer
import com.example.artabookadvancelevel.repo.FakeArtRepositoryTest
import com.example.artabookadvancelevel.roomdb.Art
import com.example.artabookadvancelevel.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class ArtDetailFragmentTest {
    //coroutine istifade etmeliyik view modelle elaqesini de goreceyik
    @get:Rule
    var hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var  fragmentFactory:ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }
    //ilk test bele olaca ArtDetailden  imageview click edende ImageApi gedilirimi

    @Test
    fun testNavigationFromArtDetailToImageApi(){
        val navControllor= Mockito.mock(NavHostController::class.java)

        launchFragmentInHiltContainer<ArtFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navControllor)
        }

        Espresso.onView(ViewMatchers.withId(com.example.artabookadvancelevel.R.id.artImageView))
            .perform(ViewActions.click())

        Mockito.verify(navControllor).navigate(
            ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment()
        )

    }

    @Test
    fun testOnBackPressed(){
        val navControllor= Mockito.mock(NavHostController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navControllor)
        }
        ViewActions.pressBack()
        //Mockito ile yoxlayiriq geri getdimi
        Mockito.verify(navControllor).popBackStack()

    }

    @Test
    fun savetest(){
        val testViewmodel=ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
        detailmodel=testViewmodel
        }
        Espresso.onView(withId(R.id.detailArtName)).perform(ViewActions.replaceText("Mona Liza"))
        Espresso.onView(withId(R.id.detailArtistName)).perform(ViewActions.replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.detailYear)).perform(ViewActions.replaceText("1600"))
        Espresso.onView(withId(R.id.detailArtName)).perform(click())

        assertThat(testViewmodel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa","Da Vinci",1500,"")
        )


    }
}