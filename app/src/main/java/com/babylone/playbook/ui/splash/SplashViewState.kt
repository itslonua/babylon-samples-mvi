package com.babylone.playbook.ui.splash

import com.babylone.playbook.core.Resource

data class SplashViewState(val resource: Resource<Boolean>) {

    companion object {
        fun init(): SplashViewState {
            return SplashViewState(Resource.loading(null))
        }
    }

}