package com.babylone.playbook.ui.main

import com.babylone.playbook.core.Resource

sealed class MainViewPartialState {

    data class Result(val data: Resource<List<MainItem>>) : MainViewPartialState()

}