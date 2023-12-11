package com.example.cw2.objects

object RegisterUtil {

    private var existingUser = listOf("gino@email.com", "fabio@email.com")

    /**
     * input is not valid if:
     * the username/password are empty
     * the username has already been taken
     * password do not match
     * password is less than 6 characters
     */

    fun validRegister(
        name: String,
        email: String,
        password: String,
    ): Boolean {
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return false
        }
        if(email in existingUser) {
            return false
        }

        if (password.count() <= 6) {
            return false
        }
        return true
    }
}