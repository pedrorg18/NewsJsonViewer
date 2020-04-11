package com.example.newsjsonviewer.features.newslist

import com.example.newsjsonviewer.domain.model.Country
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsListDomainToViewStateMapperTest {

    @Test
    fun mapCountryPanel_hasRightSelectedCountryAndOrderedList() {
        val mapper = NewsListDomainToViewStateMapper()
        val panel = mapper.mapCountryPanel(Country.Spain,
            listOf(Country.Spain, Country.France, Country.Usa))

        assertEquals(Country.Spain.name, panel.selectedCountry)

        // should get country names ordered alphabetically
        assertEquals(listOf(Country.France.name, Country.Spain.name, Country.Usa.name), panel.countryList)
    }
}