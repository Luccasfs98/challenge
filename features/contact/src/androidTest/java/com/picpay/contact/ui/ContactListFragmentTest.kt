package com.picpay.contact.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.picpay.contact.FileReader
import com.picpay.contact.MockServerDispatcher
import com.picpay.contact.launchFragmentInHiltContainer
import com.picpay.contact.R
import com.picpay.contact.di.ContactAnnotation
import com.picpay.contact.di.DispatcherModule
import com.picpay.contact.presentation.ui.fragment.ContactListFragment
import com.picpay.contact.presentation.viewState.ContactListViewState
import com.picpay.core.di.modules.BaseUrlModule
import com.picpay.core.di.modules.OkHttpClientAnnotation
import com.picpay.core.di.modules.OkHttpModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@LargeTest
@HiltAndroidTest
@UninstallModules(value = [BaseUrlModule::class])
@ExperimentalCoroutinesApi
internal class ContactListFragmentTest {

    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeBaseUrlModule {

        @Provides
        @Named("BASE_URL")
        fun provideUrl(): String = "http://localhost:8080/"
    }

    @Inject
    lateinit var okHttpClient : OkHttpClient

    @Inject
    lateinit var preferences : SharedPreferences

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()

        launchFragmentInHiltContainer<ContactListFragment>()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        preferences.edit().clear().apply()
    }

    @Test
    fun givenScreenStarted_thenTitleIsVisible()  {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttpClient))
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()

        mockWebServer.start(8080)

        Espresso.onView(withId(R.id.title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    @Test
    fun givenScreenStarted_thenProgressIsVisible()  {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        mockWebServer.start(8080)

        Espresso.onView(withId(R.id.progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun givenScreenLoadedHaveContacts_thenListIsVisible()  {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttpClient))

        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()

        mockWebServer.start(8080)

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun givenScreenLoadedAndNoHaveContacts_thenEmptyContainerIsVisible()  {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttpClient))

        mockWebServer.enqueue(MockResponse().setResponseCode(204))

        mockWebServer.start(8080)

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.emptyListContainer)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun givenScreenLoadedAndHasError_thenErrorContainerIsVisible()  {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttpClient))

        mockWebServer.enqueue(MockResponse().setResponseCode(400).setStatus("400"))

        mockWebServer.start(8080)

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.errorContainer)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun givenSwipeToRefresh_thenLoadingIsVisible()  {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(FileReader.getJsonContent("contact_list.json")))

        mockWebServer.start(8080)

        Espresso.onView(withId(R.id.swipe)).perform(ViewActions.swipeDown())

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

