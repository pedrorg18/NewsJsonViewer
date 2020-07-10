package com.example.newsjsonviewer.test.mock

import com.example.newsjsonviewer.data.database.model.DbNews
import java.util.*


fun generateRandomDbMockNews(country: String? = null) =
    DbNews(
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        null,
        randomString(),
        country
    )

private fun randomString(): String {
    return UUID.randomUUID().toString()
}

