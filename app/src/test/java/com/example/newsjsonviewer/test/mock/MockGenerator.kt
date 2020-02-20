package com.example.newsjsonviewer.test.mock

import com.google.gson.Gson
import java.io.File
import java.nio.charset.Charset


class MockGenerator {

    companion object {

        inline fun <reified T> getJsonEntityFromFileName(fileName: String): T {
            val gson = Gson()
            val jsonString = getJsonContentFromFileName(fileName)
            return gson.fromJson(jsonString, T::class.java)
        }

        fun getJsonContentFromFileName(fileName: String): String {
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

