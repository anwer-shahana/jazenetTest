package com.example.testjazenet.network


interface ResultCallback<T> {
    fun onError(code: Int, errorMessage: String)
    fun <T> onSuccess(response:T)
}

