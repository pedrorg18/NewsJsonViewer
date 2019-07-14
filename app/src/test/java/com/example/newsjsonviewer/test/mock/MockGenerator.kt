package com.example.newsjsonviewer.test.mock

import com.example.newsjsonviewer.domain.model.News
import com.example.newsjsonviewer.framework.network.mapper.NewsMapper
import com.example.newsjsonviewer.framework.network.model.NewsListEntity
import java.io.File
import java.lang.RuntimeException
import com.google.gson.Gson
import java.nio.charset.Charset





class MockGenerator {

    companion object {

        /**
         * Mock network news list model from US
         */
        fun generateMockNewsListUS(): NewsListEntity {
            return getJsonEntityFromFileName("mock_us_news_list.json")
        }

        /**
         * First domain mapped element from mock US list
         */
        fun generateFirstMockDomainNewsFromUSList(): News {
            val networkNewsList = generateMockNewsListUS()
            return NewsMapper().map(networkNewsList.articles.first())
        }

        private inline fun <reified T> getJsonEntityFromFileName(fileName: String): T {
            val gson = Gson()
            val jsonString = getJsonContentFromFileName(fileName)
            return gson.fromJson(jsonString, T::class.java)
        }


        private fun getJsonContentFromFileName(fileName: String): String {
            val file = getFile(fileName)
            return getStringContentFromFile(file)
        }

        private fun getStringContentFromFile(file: File): String {
            val inputStream = file.inputStream()
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
           return String(buffer, Charset.forName("UTF-8"))
        }

        private fun getFile(fileName: String): File {
            val resourceUrl = MockGenerator::class.java.classLoader?.getResource(fileName)
                ?: throw RuntimeException("Coouldn't get the classLoader")
            return File(resourceUrl.path)
        }

    }
}

