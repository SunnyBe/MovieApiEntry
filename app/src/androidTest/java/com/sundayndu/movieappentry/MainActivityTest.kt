package com.sundayndu.movieappentry

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sundayndu.movieappentry.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@LargeTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule(order = 2)
    var intentRule = IntentsTestRule(MainActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testExpectedViewsVisibleOnLaunch() {
        onView(withId(R.id.movie_type_latest_selection)).perform(click())
        onView(withId(R.id.progress_indicator))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.selected_movie_list))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.movie_type_latest_selection))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testAllListIsVisibleOnLaunch() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.selected_movie_list)).check(matches(isDisplayed()))
    }

    @Test
    fun testLatestListIsVisibleOnSelection() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.movie_type_latest_selection)).perform(click())
        onView(withId(R.id.selected_movie_list)).check(matches(isDisplayed()))
        onView(withText("Latest")).check(matches(isDisplayed()))
    }

    @Test
    fun testUpcomingListIsVisibleOnSelection() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.movie_type_upcoming_selection)).perform(click())
        onView(withId(R.id.selected_movie_list)).check(matches(isDisplayed()))
        onView(withText("Upcoming")).check(matches(isDisplayed()))
    }
}