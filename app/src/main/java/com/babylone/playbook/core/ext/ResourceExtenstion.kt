package com.babylone.playbook.core.ext

import com.babylone.playbook.core.Resource

fun <T> Resource<T>.bindToUi(onSuccess: (T?) -> Unit, onError: (Throwable?) -> Unit, onProgress: () -> Unit) {
    when (this.status) {
        Resource.Status.SUCCESS -> onSuccess(this.data)
        Resource.Status.ERROR -> onError(this.throwable)
        Resource.Status.IN_FLIGHT -> onProgress()
    }
}