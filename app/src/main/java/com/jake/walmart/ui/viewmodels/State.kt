package com.jake.walmart.ui.viewmodels

data class State<out T>(val status: Status, val data: T? = null, val message: String? = null) {
    companion object {

        fun<T> initial(): State<T> {
            return State(Status.INITIAL)
        }

        fun<T> loading(): State<T> {
            return State(Status.LOADING)
        }

        fun<T> success(data: T?): State<T> {
            return State(Status.SUCCESS, data = data)
        }

        fun<T> error(message: String?): State<T> {
            return State(Status.ERROR, message = message)
        }

        fun<T> noData(): State<T> {
            return State(Status.NO_DATA)
        }

    }
}

enum class Status {
    INITIAL,
    LOADING,
    SUCCESS,
    ERROR,
    NO_DATA
}