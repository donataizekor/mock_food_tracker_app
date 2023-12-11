package com.example.cw2.objects

object LoginUtil {

    /**
     * input is not valid if:
     * the username/password are empty
     */

    fun validLogin(
        name: String,
        email: String,
        password: String,
    ): Boolean {
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return false
        }

        return true
    }
}