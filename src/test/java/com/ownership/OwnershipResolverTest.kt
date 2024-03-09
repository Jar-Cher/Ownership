package com.ownership

import com.google.common.truth.Truth
import com.ownership.exceptions.MembersEmptyException
import com.ownership.exceptions.NoRuleFoundException
import com.ownership.fixtures.stub
import com.ownership.models.Member
import com.ownership.models.SourceFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OwnershipResolverTest {

    @Test
    fun `GIVEN repository has no members WHEN resolving ownership THEN it should throw an exception`() {
        val ownershipResolver = OwnershipResolver(
            members = emptyList(),
            ownershipRules = emptyList(),
        )
        val sourceFile = SourceFile(path = "/path/to/file1.kt")
        assertThrows<MembersEmptyException> {
            ownershipResolver.resolveOwnership(sourceFile)
        }
    }

    @Test
    fun `GIVEN a file with no ownership rules WHEN resolving ownership THEN it should throw an exception`() {
        val ownershipResolver = OwnershipResolver(
            members = listOf(
                Member.stub()
            ),
            ownershipRules = emptyList(),
        )
        val sourceFile = SourceFile(path = "/path/to/file1.kt")
        val result = assertThrows<NoRuleFoundException> {
            ownershipResolver.resolveOwnership(sourceFile)
        }
        Truth.assertThat(result.message).isEqualTo("No rule found for file: /path/to/file1.kt")
    }
}