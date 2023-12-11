package com.example.cw2.objects

object AddItemUtil {
    /**
     * input is not valid if:
     * item name/ location field are empty
     */

    fun validAddItem(
        name: String,
        location: String
    ): Boolean {
        if (name.isEmpty() || location.isEmpty()) {
            return false
        }
        return true
    }
}