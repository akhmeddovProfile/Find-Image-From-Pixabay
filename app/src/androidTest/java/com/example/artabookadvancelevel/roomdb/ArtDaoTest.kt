package com.example.artabookadvancelevel.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Dao
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artabookadvancelevel.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

//@SmallTest is an Android JUnit4 test annotation used to indicate that a test is a small,
            // focused unit test that should run quickly and not depend on any external resources
            //JUnit4 is a testing framework for Java programming language, used to write and run repeatable tests.
 //         It is an open-source tool that is widely used for unit testing in Java applications, including Android.

//Room Database Testing istifadesi
 @SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {
     @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()
     private lateinit var dao:ArtDao

     @get:Rule
     var hiltRule=HiltAndroidRule(this)

     @Inject
     @Named("testDatabase")
     lateinit var artDatabase: ArtDatabase

     @Before
     fun setup(){
         //burada bildiririk ki Main Thread isleyeceyik
/*         artDatabase=Room.inMemoryDatabaseBuilder(
             ApplicationProvider.getApplicationContext(),ArtDatabase::class.java
         ).allowMainThreadQueries().build()*/
         dao=artDatabase.artDao()
         hiltRule.inject()
     }
     //her seferinde inMemoryDatabase yaradilacaq
     @After
     fun afterfinishSetUp(){
         artDatabase.close()
     }

     //burada normal insert emeliyyati bas verecek
     //runBlocking sira sira edir bir thread bitmeden o birisine kecmir
     @Test
     fun insertArtTesting()= runBlockingTest{
        //ArtDao suspend fun oldugu ucun coroutine block istifade etmek lazim olacaq
         val exampleArt=Art("Mona Lisa","Da Vinci",1700,"test.com",1)
         dao.insertAll(exampleArt)

         //sonra ise yoxlamaq isteye bilerik bu elave edilib ya yox

         val list=dao.observeAll().getOrAwaitValue()
         //listin icinde example art olub olmadigini sececeyik
         assertThat(list).contains(exampleArt)

     }
     @Test
     fun deleteArtTesting()= runBlockingTest{
        val deleteexampleart=Art("Mona Lisa","Da Vinci",1700,"test.com",1)
         dao.insertAll(deleteexampleart)
         dao.deleteItem(deleteexampleart)

         val listfordelete=dao.observeAll().getOrAwaitValue()
         assertThat(listfordelete).doesNotContain(deleteexampleart)

     }
}