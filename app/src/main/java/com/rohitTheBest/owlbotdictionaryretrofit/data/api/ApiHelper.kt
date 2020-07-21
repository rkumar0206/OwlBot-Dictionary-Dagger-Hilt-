package com.rohitTheBest.owlbotdictionaryretrofit.data.api

import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Dictionary
import retrofit2.Response

interface ApiHelper {

    suspend fun getMeaning(
        word: String
    ): Response<Dictionary>
}