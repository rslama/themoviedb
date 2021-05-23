package com.slama.themoviedb.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.R
import com.slama.themoviedb.TheMovieDBApplication
import com.slama.themoviedb.di.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@UninstallModules(ViewModelProvider::class)
class MovieDetailActivityTest {



    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_intent")
    lateinit var intent: Intent

    @Before
    fun setup(){
        hiltRule.inject()
        ActivityScenario.launch<MovieDetailActivity>(intent)
    }

    @Test
    fun when_collection_is_present_collectionview_should_be_visible() {

        onView(withId(R.id.detail_collection_scrollview))
            .check(matches(isDisplayed()))
    }


}