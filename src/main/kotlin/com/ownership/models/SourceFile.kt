package com.ownership.models

import org.example.parsers.FileChange

data class SourceFile(val path: String) {

    fun resolve(member: String, fileChange: FileChange) {

    }

    fun resolve(member: Member, fileChange: FileChange) {

    }
}
