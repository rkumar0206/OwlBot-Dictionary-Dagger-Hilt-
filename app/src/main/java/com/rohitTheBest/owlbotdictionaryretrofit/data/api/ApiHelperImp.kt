package com.rohitTheBest.owlbotdictionaryretrofit.data.api

import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Dictionary
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(
    val apiService: ApiService
) : ApiHelper {

    override suspend fun getMeaning(word: String): Response<Dictionary> =
        apiService.getMeaning(
            word = word
        )
}