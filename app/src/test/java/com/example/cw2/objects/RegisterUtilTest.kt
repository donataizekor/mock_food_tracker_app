package com.example.cw2.objects


import org.junit.Test
import com.google.common.truth.Truth.*

class RegisterUtilTest{
    @Test
    fun `empty name, email or password returns false`(){
        val result = RegisterUtil.validRegister(
            "",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `already existing email return false`(){
        val result = RegisterUtil.validRegister(
            "Gino",
            "gino@email.com",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password less that 6 characters digits returns false`() {
        val result = RegisterUtil.validRegister(
            "Dina",
            "dina@gmail.com",
            "asdfgh"
        )
        assertThat(result).isFalse()
    }
}