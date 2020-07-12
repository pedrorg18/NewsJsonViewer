package com.example.newsjsonviewer.test.mock

import com.example.newsjsonviewer.data.database.model.CachedNews
import com.example.newsjsonviewer.data.database.model.DbNews
import com.example.newsjsonviewer.domain.model.Country
import com.example.newsjsonviewer.test.factory.randomLong
import java.util.*

fun createCachedNews(country: Country, timestamp: Long) =
    CachedNews(
        country.name,
        timestamp
    )

fun createRandomDbNews(country: String) =
    DbNews(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        randomLong(),
        UUID.randomUUID().toString(),
        country
    )