package com.babylone.playbook.core

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable?
) {

    companion object {

        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(throwable: Throwable, data: T? = null): Resource<T> = Resource(Status.ERROR, data, throwable)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.IN_FLIGHT, data, null)

    }

    fun <R> map(mapper: ((T?) -> R)? = null): Resource<R> {
        return Resource(status, mapper?.invoke(data), throwable)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        IN_FLIGHT
    }
}