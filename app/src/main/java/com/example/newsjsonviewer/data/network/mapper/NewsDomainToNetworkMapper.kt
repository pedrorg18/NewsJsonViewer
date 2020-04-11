package com.example.newsjsonviewer.data.network.mapper

import com.example.newsjsonviewer.data.network.*
import com.example.newsjsonviewer.domain.model.Country

class NewsDomainToNetworkMapper {

    /**
     * country code used by the remote API
     */
    fun mapCountryCode(country: Country) =
        when(country) {
            Country.Usa -> COUNTRY_CODE_US
            Country.Australia -> COUNTRY_CODE_AUSTRALIA
            Country.Uk -> COUNTRY_CODE_UK
            Country.Japan -> COUNTRY_CODE_JAPAN
            Country.France -> COUNTRY_CODE_FRANCE
            Country.Germany -> COUNTRY_CODE_GERMANY
            Country.Russia -> COUNTRY_CODE_RUSSIA
            Country.Italy -> COUNTRY_CODE_ITALY
        }
}