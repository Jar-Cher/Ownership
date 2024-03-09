package com.ownership.models

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class OwnershipRuleTest {

    @Test
    fun `GIVEN two objects that represent the same ownership rule WHEN compared THEN they should be considered equal`() {
        val ownershipRule1 = OwnershipRule(
            teamTag = "team1",
            glob = "glob1",
        )
        val ownershipRule2 = OwnershipRule(
            teamTag = "team1",
            glob = "glob1",
        )
        Truth.assertThat(ownershipRule1).isEqualTo(ownershipRule2)
    }

    @Test
    fun `GIVEN two objects that represent different ownership rules WHEN compared THEN they should not be considered equal`() {
        val ownershipRule1 = OwnershipRule(
            teamTag = "team1",
            glob = "glob1",
        )
        val ownershipRule2 = OwnershipRule(
            teamTag = "team2",
            glob = "glob2",
        )
        Truth.assertThat(ownershipRule1).isNotEqualTo(ownershipRule2)
    }
}