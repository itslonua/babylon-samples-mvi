package com.babylone.playbook.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.babylone.playbook.R
import com.babylone.playbook.core.ext.bindToUi
import com.babylone.playbook.ui.main.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class SplashActivity : AppCompatActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        presenter.initialize()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }

    override fun renderView(state: SplashViewState) {
        state.resource.bindToUi({
            startActivity(MainActivity.newIntent(this))
            finish()
        }, {
            Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
        }, {

        })
    }

}
