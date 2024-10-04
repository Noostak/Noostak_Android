package com.sopt.domain.usecase

import com.sopt.domain.repository.ExampleRepository
import javax.inject.Inject

class GetExampleUseCase @Inject constructor(
    private val exampleRepository: ExampleRepository
) {
    suspend operator fun invoke(page: Int) = exampleRepository.getUsers(page)
}