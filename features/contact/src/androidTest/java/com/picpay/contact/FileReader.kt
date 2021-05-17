package com.picpay.contact

import java.io.InputStreamReader

object FileReader {

    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(fileName)).use { it.readText() }
    }

}