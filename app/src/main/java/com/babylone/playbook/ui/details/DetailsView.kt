package com.babylone.playbook.ui.details

import com.babylone.playbook.core.mvp.MviView

interface DetailsView : MviView {

    fun renderView(detailsViewState: DetailsViewState)

}