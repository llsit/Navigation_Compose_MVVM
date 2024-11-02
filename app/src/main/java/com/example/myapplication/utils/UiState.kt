package com.example.myapplication.utils

sealed class UiState<out T> {

    data class Success<T>(val data: T?) : UiState<T>()

    data class Error<T>(val message: String) : UiState<T>()

    object Loading : UiState<Nothing>()

}