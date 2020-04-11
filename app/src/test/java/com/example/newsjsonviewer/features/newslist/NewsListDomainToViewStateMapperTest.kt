package com.example.newsjsonviewer.features.newslist

import com.example.newsjsonviewer.domain.model.Country
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsListDomainToViewStateMapperTest {

    @Test
    fun mapCountryPanel_hasRightSelectedCountryAndOrderedList() {
        val mapper = NewsListDomainToViewStateMapper()
        val panel = mapper.mapCountryPanel(Country.Australia,
            listOf(Country.France, Country.Australia, Country.Usa))

        assertEquals(Country.Australia.name, panel.selectedCountry)

        // should get country names ordered alphabetically
        assertEquals(listOf(Country.Australia.name, Country.France.name, Country.Usa.name), panel.countryList)
    }
}