package com.example.artabookadvancelevel.view

import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.ktx.R
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.artabookadvancelevel.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.runner.Version.id
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @Inject
    lateinit var artFragmentFactory: ArtFragmentFactory
    @get:Rule
    var hiltRule= HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    //1.navController Mockunu cixardiriq
    //2.HiltContainer icinde mock basladiriq
    //3.Espresso dedik fab tap click et
    //4.Mockito dedik navigation islemi olacaq onu dogrula
    fun testNavigationFromArtToArtDetail(){
        //Fake-> yalan bir repository(icerisinde limitli funksiyalar saxlayan yeni utdurma lis)
        //Mocks ise sinifin ozunu kopyalayiriq ve sinifin icinde lazim olan funksiyalari istfade edirik
        //Burada Navigation fake etmeyimiz lazimdir test elemek ucun
        val navControllor=Mockito.mock(NavHostController::class.java)

        //Hilt istifade etmeseydik launchInCotainer istifade ede bilerdik bu is ucun
        launchFragmentInHiltContainer<ArtFragment>(
            factory = artFragmentFactory
        ){
        Navigation.setViewNavController(requireView(),navControllor)
        }
        //Belelikle yaratdigimiz  bu Mock gorunumde istifade ede bilerik
        //mesele button click edende neynemek isteyirik ve s tipli UI isleri ucun Espresso

        //1.direk id esasen gorunumu teyin etmis oluruq
        //2.Hemen id click etsin... Hemen bu click xarici hansi hadiseler
        // oldugunu gormek isteyirikse ViewActions. desek tapariq
        Espresso.onView(ViewMatchers.withId(com.example.artabookadvancelevel.R.id.fab))
            .perform(click())

        //bundan sonra Mochitoya dogrulama etmesini isteyirik
        Mockito.verify(navControllor).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailFragment()
        )

    }
}