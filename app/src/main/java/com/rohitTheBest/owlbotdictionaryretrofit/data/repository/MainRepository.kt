package com.rohitTheBest.owlbotdictionaryretrofit.data.repository

import com.rohitTheBest.owlbotdictionaryretrofit.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    val apiHelper: ApiHelper
) {

    suspend fun getMeaning(word: String) = apiHelper.getMeaning(word)
}