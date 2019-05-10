package com.babylone.playbook.ui.splash

import com.babylone.playbook.core.Resource

sealed class SplashPartialViewState {

    data class Result(val resource: Resource<Boolean>) : SplashPartialViewState()

}