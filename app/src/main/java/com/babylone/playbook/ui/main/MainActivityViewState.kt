package com.babylone.playbook.ui.main

import com.babylone.playbook.core.Resource

data class MainActivityViewState(val resource: Resource<List<MainItem>>) {

    companion object {

        fun init() = MainActivityViewState(Resource.loading())

    }

}