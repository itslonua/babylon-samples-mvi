package com.babylone.playbook.ui.details

import com.babylone.playbook.core.Resource

sealed class DetailsPartialViewState {

    data class Result(val resource: Resource<DetailPostItem>) : DetailsPartialViewState()

}