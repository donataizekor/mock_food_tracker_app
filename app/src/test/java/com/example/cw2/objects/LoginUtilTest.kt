package com.example.cw2.objects

import org.junit.Test
import com.google.common.truth.Truth.*

class LoginUtilTest{
    @Test
    fun `empty name, email or password returns false`(){
        val result = LoginUtil.validLogin(
            "",
            "",
            ""
        )
        assertThat(result).isFalse()
    }
}