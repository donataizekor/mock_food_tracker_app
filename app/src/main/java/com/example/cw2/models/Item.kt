package com.example.cw2.models

data class Item(
        var id: String = "",
        var name: String = "",
        var quantity: String = "",
        var image_url: String = "",
        var creation_time: Long = 0,
        var expiring_date: String = "",
        var user: String = "",
        var type: String = ""
)