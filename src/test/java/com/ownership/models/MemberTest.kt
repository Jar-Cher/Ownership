package com.ownership.models

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class MemberTest {

    @Test
    fun `GIVEN two objects that represent the same person WHEN compared THEN they should be considered equal`() {
        val member1 = Member(
            name = "John Konstantin",
            teamTag = "team1",
            tag = "john",
        )
        val member2 = Member(
            name = "John Konstantin",
            teamTag = "team1",
            tag = "john",
        )
        Truth.assertThat(member1).isEqualTo(member2)
    }

    @Test
    fun `GIVEN two objects that represent different people WHEN compared THEN they should not be considered equal`() {
        val member1 = Member(
            name = "John",
            teamTag = "team1",
            tag = "john"
        )
        val member2 = Member(
            name = "Jane",
            teamTag = "team1",
            tag = "john"
        )
        Truth.assertThat(member1).isNotEqualTo(member2)
    }
}