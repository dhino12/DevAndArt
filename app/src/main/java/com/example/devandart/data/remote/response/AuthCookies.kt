package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthCookiesResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("auth")
    val auth: Boolean? = null,
)
