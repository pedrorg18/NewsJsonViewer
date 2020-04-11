package com.example.newsjsonviewer.domain.model

sealed class Country(val name: String) {
    object Usa : Country("United States")
    object Australia : Country("Australia")
    object Uk : Country("United Kingdom")
    object Japan : Country("Japan")
    object France : Country("France")
    object Germany : Country("Germany")
    object Russia : Country("Russia")
    object Italy : Country("Italy")

    companion object {
        fun all() = listOf(
            Usa,
            Australia,
            Uk,
            Japan,
            France,
            Germany,
            Russia,
            Italy
        )
    }
}