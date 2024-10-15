package com.sopt.data.datasource

import com.sopt.data.dto.ExampleBaseResponse
import com.sopt.data.dto.response.ResponseGetExampleDto

interface ExampleDataSource {
    suspend fun getUsers(page: Int): ExampleBaseResponse<List<ResponseGetExampleDto>>
}