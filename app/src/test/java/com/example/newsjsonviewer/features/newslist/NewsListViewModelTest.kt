package com.example.newsjsonviewer.features.newslist

import android.widget.ImageView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsjsonviewer.data.network.COUNTRY_CODE_US
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.domain.usecases.GetNewsUseCase
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class NewsListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var vm: NewsListViewModel
    private lateinit var getNewsUseCase: GetNewsUseCase
    private lateinit var subscriberScheduler: TestScheduler
    private lateinit var observerScheduler: TestScheduler

    @Before
    fun setUp() {
        getNewsUseCase = mock(GetNewsUseCase::class.java)

        subscriberScheduler = TestScheduler()
        observerScheduler = TestScheduler()

        vm = NewsListViewModel(getNewsUseCase, subscriberScheduler, observerScheduler)
    }


    @Test
    fun onEventScreenLoad_dataIsDisplayed() {

        `when`(getNewsUseCase.get(COUNTRY_CODE_US)).thenReturn(
            Single.just(
                listOf(
                    mockNews(1),
                    mockNews(2)
                )
            )
        )

        vm.onEvent(NewsListEvent.ScreenLoadEvent)

        // check loading state
        assertEquals(NewsListViewState.Loading, vm.viewStateLD.value)

        subscriberScheduler.triggerActions()
        observerScheduler.triggerActions()

        // check get use case was called
        verify(getNewsUseCase, times(1)).get(COUNTRY_CODE_US)

        // check state content and value is expected
        assertTrue(vm.viewStateLD.value is NewsListViewState.Content)
        assertEquals("title1",
            (vm.viewStateLD.value as NewsListViewState.Content).content.newsList[0].title)
    }

    @Test
    fun onEventScreenLoad_errorIsDisplayed() {
        `when`(getNewsUseCase.get(COUNTRY_CODE_US)).thenReturn(
            Single.error(
                RuntimeException("Test error")
            )
        )

        vm.onEvent(NewsListEvent.ScreenLoadEvent)

        // check loading state
        assertEquals(NewsListViewState.Loading, vm.viewStateLD.value)

        subscriberScheduler.triggerActions()
        observerScheduler.triggerActions()

        // check get use case was called
        verify(getNewsUseCase, times(1)).get(COUNTRY_CODE_US)

        // check state content and value is expected
        assertTrue(vm.viewStateLD.value is NewsListViewState.Error)
        assertEquals("Test error",
            (vm.viewStateLD.value as NewsListViewState.Error).message)
    }

    @Test
    fun onEventScreenReload_dataIsDisplayed() {
        `when`(getNewsUseCase.get(COUNTRY_CODE_US)).thenReturn(
            Single.just(
                listOf(
                    mockNews(1),
                    mockNews(2)
                )
            )
        )

        vm.onEvent(NewsListEvent.ScreenLoadEvent)

        // check loading state
        assertEquals(NewsListViewState.Loading, vm.viewStateLD.value)

        subscriberScheduler.triggerActions()
        observerScheduler.triggerActions()

        // check get use case was called
        verify(getNewsUseCase, times(1)).get(COUNTRY_CODE_US)

        // check state content and value is expected
        assertTrue(vm.viewStateLD.value is NewsListViewState.Content)
        assertEquals("title1",
            (vm.viewStateLD.value as NewsListViewState.Content).content.newsList[0].title)
    }

    @Test
    fun onEventElementClick_goToDetailsEffectIsSent() {
        `when`(getNewsUseCase.get(COUNTRY_CODE_US)).thenReturn(
            Single.just(
                listOf(
                    mockNews(1),
                    mockNews(2)
                )
            )
        )

        // load screen
        sendEventAndTriggerActions(NewsListEvent.ScreenLoadEvent)

        val mockNews = mockNews(2)
        val mockImageView = mock(ImageView::class.java)
        vm.onEvent(NewsListEvent.ElementClickEvent(mockNews, mockImageView))

        // check load details effect was called with the right model
        assertTrue(vm.viewEffectsLD.value is NewsListViewEffect.LoadDetailsEffect)
        assertEquals(mockNews,
            (vm.viewEffectsLD.value as NewsListViewEffect.LoadDetailsEffect).news)
    }

    @Test
    fun onChangeCountryClick_showSelectionPanel() {
        `when`(getNewsUseCase.get(COUNTRY_CODE_US)).thenReturn(
            Single.just(
                listOf(
                    mockNews(1),
                    mockNews(2)
                )
            )
        )

        // load screen
        sendEventAndTriggerActions(NewsListEvent.ScreenLoadEvent)

        // click on "change country"
        sendEventAndTriggerActions(NewsListEvent.ChangeCountryClickEvent)

        // check content state and default country (USA) selected
        assertTrue(vm.viewStateLD.value is NewsListViewState.Content)
        assertEquals(Country.Usa.name,
            (vm.viewStateLD.value as NewsListViewState.Content).content.countrySelectionPanel!!.selectedCountry)
    }



    private fun mockNews(mockId: Int) =
        News(
            "title$mockId",
            "desc$mockId",
            "image$mockId",
            "author$mockId",
            "src$mockId",
            null,
            null
        )

    private fun sendEventAndTriggerActions(event: NewsListEvent) {
        vm.onEvent(event)
        subscriberScheduler.triggerActions()
        observerScheduler.triggerActions()
    }
}