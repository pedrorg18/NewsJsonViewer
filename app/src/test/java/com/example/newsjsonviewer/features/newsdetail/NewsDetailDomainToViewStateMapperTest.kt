package com.example.newsjsonviewer.features.newsdetail

import com.example.newsjsonviewer.test.mock.generateFirstMockDomainNewsFromUSList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NewsDetailDomainToViewStateMapperTest {

    @Test
    fun map() {
        val news = generateFirstMockDomainNewsFromUSList()
        val detailViewState = NewsDetailDomainToViewStateMapper()
            .map(news)
        with(detailViewState.content) {
            assertEquals("https://sportshub.cbsistatic.com/i/r/2019/07/13/cb4a9511-605b-4571-8d03-060d65e1d31d/thumbnail/1200x675/6b18dbf46bc11fd6f41c7e88abc7846c/usatsi-13024725.jpg",
                imageUrl)
            assertEquals("Roger Federer vs. Novak Djokovic live score: Wimbledon 2019 final updates as the championship heads to a fifth set - CBS Sports",
                title)
            assertEquals("The matchup in 2019 finals at the All England Club is all we could have hoped for",
                subTitle)
            assertEquals("Andrew Julian · 14-07-2019 19:13", authorAndDate)
            assertEquals("Source: Cbssports.com", source)
            assertTrue(content.contains("Could it possibly be any bigger?"))
        }
    }
}