package com.example.cw2.objects

import org.junit.Test
import com.google.common.truth.Truth.*

class AddItemUtilTest{
    @Test
    fun `empty name and location returns false`(){
        val result = AddItemUtil.validAddItem(
            "",
            ""
        )
        assertThat(result).isFalse()
    }


}