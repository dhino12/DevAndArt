package com.example.devandart.data.remote

sealed class ResultFetch<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultFetch<T>()
    data class Error(val error: String) : ResultFetch<Nothing>()
    object Loading : ResultFetch<Nothing>()
}