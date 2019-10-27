package com.example.todoapp.api

data class WrappedResponse<T>(
    val statusCode: Int,
    val serverTime: Long = -1,
    val errorMessage: String? = null,
    val body: T? = null
) {
    fun isSuccessful(): Boolean {
        return statusCode == 200
    }

    fun onSuccessful(unit: () -> Unit) {
        unit.invoke()
    }
}