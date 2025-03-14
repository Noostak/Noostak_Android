package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetOptionsDto
import com.sopt.data.dto.response.ResponseGetOptionsOptionDto
import com.sopt.data.dto.response.ResponseGetOptionsPrioritiesDto
import com.sopt.data.dto.response.base.BaseMyInfoDto
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.IdentityEntity
import com.sopt.domain.entity.OptionEntity
import com.sopt.domain.entity.RecommendationPriorityEntity

fun ResponseGetOptionsDto.toAppointmentEntity() = AppointmentEntity(
    isHost = isAppointmentHost,
    recommendationPriority = priorities.map {
        it?.toRecommendationPriorityEntity() ?: RecommendationPriorityEntity(-1, emptyList())
    }
)

fun ResponseGetOptionsPrioritiesDto.toRecommendationPriorityEntity() = RecommendationPriorityEntity(
    priority = priority,
    options = options.map {
        it?.toOptionEntity() ?: OptionEntity(
            -1,
            -1,
            IdentityEntity("", -1, ""),
            "",
            "",
            "",
            -1,
            false,
            -1,
            emptyList(),
            -1,
            emptyList()
        )
    }
)

fun ResponseGetOptionsOptionDto.toOptionEntity() = OptionEntity(
    id = optionId.toLong(),
    totalMemberCount = groupMemberCount,
    myIdentity = myInfo?.toIdentityEntity() ?: IdentityEntity("unavailable", -1, "나"),
    date = appointmentOptionTime.date,
    startTime = appointmentOptionTime.startTime,
    endTime = appointmentOptionTime.endTime,
    likes = likes,
    liked = liked,
    availableMemberCount = availableMemberCount,
    availableMembers = availableFriends?.names ?: emptyList(),
    unavailableMemberCount = unavailableFriends?.count ?: 0,
    unavailableMembers = unavailableFriends?.names ?: emptyList()
)

fun BaseMyInfoDto.toIdentityEntity() = IdentityEntity(
    availability = availability,
    position = position,
    name = name
)
