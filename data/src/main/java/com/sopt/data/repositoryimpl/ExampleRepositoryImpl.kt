package com.sopt.data.repositoryimpl

import com.sopt.data.datasource.ExampleDataSource
import com.sopt.data.mapper.toExampleEntity
import com.sopt.domain.entity.ExampleEntity
import com.sopt.domain.repository.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val exampleDataSource: ExampleDataSource
) : ExampleRepository {
    override suspend fun getUsers(page: Int): Result<List<ExampleEntity>> {
        return runCatching {
            exampleDataSource.getUsers(page).data?.map { it.toExampleEntity() } ?: emptyList()
        }
    }
}