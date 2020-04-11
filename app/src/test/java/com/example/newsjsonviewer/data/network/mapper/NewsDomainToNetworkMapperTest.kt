package com.example.newsjsonviewer.data.network.mapper

import com.example.newsjsonviewer.data.network.COUNTRY_CODE_AUSTRALIA
import com.example.newsjsonviewer.data.network.COUNTRY_CODE_UK
import com.example.newsjsonviewer.domain.model.Country
import org.junit.Test

import org.junit.Assert.*

class NewsDomainToNetworkMapperTest {

    @Test
    fun mapCountryCode() {
        val mapper = NewsDomainToNetworkMapper()

        assertEquals(COUNTRY_CODE_AUSTRALIA, mapper.mapCountryCode(Country.Australia))
        assertEquals(COUNTRY_CODE_UK, mapper.mapCountryCode(Country.Uk))
    }
}