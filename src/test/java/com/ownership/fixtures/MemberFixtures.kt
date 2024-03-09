package com.ownership.fixtures

import com.ownership.models.Member

fun Member.Companion.stub(
    name: String = "John Doe",
    teamTag: String = "team",
    tag: String = "tag"
) = Member(name, teamTag, tag)