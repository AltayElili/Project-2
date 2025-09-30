package com.example.project_2.service

sealed class NetworkResource<out T> {
    data class Success<T>(val data: T) : NetworkResource<T>()
    data class Error(val message: String?) : NetworkResource<Nothing>()
}