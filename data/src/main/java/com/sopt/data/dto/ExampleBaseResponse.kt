package com.sopt.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleBaseResponse<T>(
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("total") val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("data") val data: T? = null,
    @SerialName("support") val support: Support,
)

@Serializable
data class Support(
    @SerialName("url") val url: String,
    @SerialName("text") val text: String,
)