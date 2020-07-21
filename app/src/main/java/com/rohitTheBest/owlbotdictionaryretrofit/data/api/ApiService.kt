package com.rohitTheBest.owlbotdictionaryretrofit.data.api

import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Dictionary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("Authorization: Token 28280911157414d5a*****383928ff06c99cf03b")
    @GET("dictionary/{word}")
    suspend fun getMeaning(
        @Path("word") word: String
    ): Response<Dictionary>
}