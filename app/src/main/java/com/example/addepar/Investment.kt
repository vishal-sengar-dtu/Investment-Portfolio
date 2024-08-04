package com.example.addepar

import java.io.Serializable

data class Investment(
    val name: String,
    val ticker: String?,
    val value: String,
    val principal: String,
    val details: String
) : Serializable
