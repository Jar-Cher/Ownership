package com.ownership

import com.ownership.exceptions.MembersEmptyException
import com.ownership.exceptions.NoRuleFoundException
import com.ownership.models.Member
import com.ownership.models.OwnershipRule
import com.ownership.models.SourceFile

class OwnershipResolver(
    val members: List<Member>,
    val ownershipRules: List<OwnershipRule>
) {
    fun resolveOwnership(sourceFile: SourceFile) {
        if (members.isEmpty()) {
            throw MembersEmptyException()
        } else {
            throw NoRuleFoundException(sourceFile.path)
        }
    }
}
