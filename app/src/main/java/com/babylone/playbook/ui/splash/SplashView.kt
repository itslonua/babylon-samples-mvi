package com.babylone.playbook.ui.splash

import com.babylone.playbook.core.mvp.MviView

interface SplashView : MviView {

    fun renderView(state: SplashViewState)

}