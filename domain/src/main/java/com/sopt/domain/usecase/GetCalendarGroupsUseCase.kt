package com.sopt.domain.usecase

import com.sopt.domain.repository.GroupRepository
import javax.inject.Inject

class GetCalendarGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke() = groupRepository.getGroups()
}
