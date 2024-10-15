package com.sopt.data.datasourceimpl

import com.sopt.data.datasource.ExampleDataSource
import com.sopt.data.dto.ExampleBaseResponse
import com.sopt.data.dto.response.ResponseGetExampleDto
import com.sopt.data.service.ExampleApiService
import javax.inject.Inject

class ExampleDataSourceImpl @Inject constructor(
    private val exampleApiService: ExampleApiService
) : ExampleDataSource {
    override suspend fun getUsers(page: Int): ExampleBaseResponse<List<ResponseGetExampleDto>> {
        return exampleApiService.getUsers(page)
    }
}