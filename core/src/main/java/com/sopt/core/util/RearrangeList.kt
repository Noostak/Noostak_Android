package com.sopt.core.util

import com.sopt.domain.entity.IdentityEntity

class RearrangeList {
    fun rearrangeMembersBasedOnAvailability(
        identity: IdentityEntity,
        data: List<String>
    ): List<String> {
        val membersList = data.toMutableList()
        if (identity.position in membersList.indices && membersList[identity.position] == identity.name) {
            val myInfo = membersList.removeAt(identity.position)
            membersList.add(0, myInfo)
        }
        return membersList
    }
}
