package com.sopt.domain.repository

import com.sopt.domain.entity.ExampleEntity

interface ExampleRepository {
    suspend fun getUsers(page: Int): Result<List<ExampleEntity>>
}