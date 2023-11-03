package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class MangasResponse (
    @field:SerializedName("body")
    val body:ResultsItemIllustration
)