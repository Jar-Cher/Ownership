package com.ownership.models

import com.google.common.truth.Truth
import org.junit.jupiter.api.Test

class SourceFileTest {

    @Test
    fun `GIVEN two objects that represent the same file WHEN compared THEN they should be considered equal`() {
        val sourceFile1 = SourceFile(path = "file1")
        val sourceFile2 = SourceFile(path = "file1")
        Truth.assertThat(sourceFile1).isEqualTo(sourceFile2)
    }

    @Test
    fun `GIVEN two objects that represent different files WHEN compared THEN they should not be considered equal`() {
        val sourceFile1 = SourceFile(path = "file1")
        val sourceFile2 = SourceFile(path = "file2")
        Truth.assertThat(sourceFile1).isNotEqualTo(sourceFile2)
    }
}