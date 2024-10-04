package com.sopt.data.mapper

import com.sopt.data.dto.response.ResponseGetExampleDto
import com.sopt.domain.entity.ExampleEntity

fun ResponseGetExampleDto.toExampleEntity() = ExampleEntity(
    email, firstName, avatar
)