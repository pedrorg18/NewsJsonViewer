package com.example.newsjsonviewer.features.newslist

import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.domain.model.News

class NewsListDomainToViewStateMapper {

    fun mapList(domainList: List<News>) =
        NewsListViewState.Content(
            NewsListViewStateContent(
                domainList.map {
                    NewsListElementViewStateContent(
                        it.title,
                        it.description,
                        it.imageUrl,
                        it
                    )
                },
                null
            )
        )

    fun mapCountryPanel(
        selectedCountry: Country,
        list: List<Country>
    ) =
        CountrySelectionPanel(
            selectedCountry.name,
            mapCountryList(list)
        )

    private fun mapCountryList(list: List<Country>) =
        list
            .sortedBy { it.name }
            .map {
                it.name
            }
}