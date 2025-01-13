package com.sopt.core.util

import com.sopt.domain.entity.IdentityEntity

class RearrangeList {
    fun rearrangeMembersBasedOnAvailability(
        identity: IdentityEntity,
        data: List<String>
    ): List<String> {
        val membersList = data.toMutableList()
        // Validate position is within bounds and compare with name
        if (identity.position in membersList.indices && membersList[identity.position] == identity.name) {
            val myInfo = membersList.removeAt(identity.position) // Remove the element
            membersList.add(0, myInfo) // Add it to the front
        }

        return membersList
    }
}
