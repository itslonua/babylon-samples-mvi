package com.babylone.playbook.ui.details

import com.babylone.playbook.core.Resource

data class DetailsViewState(
    val resource: Resource<DetailPostItem>
) {
    companion object {

        fun init(): DetailsViewState {
            return DetailsViewState(Resource.loading(null))
        }

    }
}