package com.babylone.playbook.ui.main

import com.babylone.playbook.core.mvp.MviView

interface MainActivityView : MviView {

    fun renderView(state: MainActivityViewState)

}