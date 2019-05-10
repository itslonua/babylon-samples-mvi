package com.babylone.playbook.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.babylone.playbook.R
import com.babylone.playbook.core.ext.bindToUi
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainActivityView {

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var presenter: MainActivityPresenter
    private lateinit var postAdapter: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postAdapter = MainActivityAdapter(presenter::onPostClicked)
        with(activity_main_recycler) {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(MainActivityItemDecorator(resources.getDimension(R.dimen.list_offset).toInt()))
        }

        activity_main_refresh.setOnRefreshListener { presenter.reloadScreen() }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        presenter.reloadScreen()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }

    override fun renderView(state: MainActivityViewState) {
        val resource = state.resource
        state.resource.bindToUi({
            activity_main_progress.visibility = View.GONE
            activity_main_refresh.isRefreshing = false
            resource.data?.let { postAdapter.swap(it) }
        }, {
            activity_main_progress.visibility = View.GONE
            activity_main_refresh.isRefreshing = false
            Toast.makeText(this, resource.throwable?.message, Toast.LENGTH_SHORT).show()
        }, {
            activity_main_progress.visibility = View.VISIBLE
        })
    }

}
