package com.example.newsjsonviewer.features.newsdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsjsonviewer.domain.model.News
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class NewsDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var vm: NewsDetailViewModel

    @Before
    fun setUp() {
        vm = NewsDetailViewModel()
    }

    @Test
    fun onEventLoadScreen_checkDataDisplayed() {
        vm.onEvent(NewsDetailEvent.LoadDetailScreen(mockNews(1)))

        assertTrue(vm.viewStateLiveData.value is NewsDetailViewState.Content)

        assertEquals("title1",
            (vm.viewStateLiveData.value as NewsDetailViewState.Content).content.title)

    }

    private fun mockNews(@Suppress("SameParameterValue") mockId: Int) =
        News(
            "title$mockId",
            "desc$mockId",
            "image$mockId",
            "author$mockId",
            "src$mockId",
            Date(),
            "content$mockId"
        )
}