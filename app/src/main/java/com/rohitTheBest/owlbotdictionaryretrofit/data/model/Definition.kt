package com.rohitTheBest.owlbotdictionaryretrofit.data.model

data class Definition(
    val definition: String?,
    val emoji: Any? = null,
    val example: String?,
    val image_url: String?,
    val type: String?
)