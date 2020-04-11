package com.example.newsjsonviewer.domain.model

sealed class Country(val name: String) {
    object Usa : Country("United States")
    object Spain : Country("Spain")
    object Uk : Country("United Kingdom")
    object Japan : Country("Japan")
    object France : Country("France")
    object Germany : Country("Germany")
    object Russia : Country("Russia")
    object China : Country("China")
    object Italy : Country("Italy")

    companion object {
        fun all() = listOf(
            Usa,
            Spain,
            Uk,
            Japan,
            France,
            Germany,
            Russia,
            China,
            Italy
        )
    }
}