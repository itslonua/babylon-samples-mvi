package com.babylone.playbook.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.babylone.playbook.R
import com.babylone.playbook.core.ImageLoaderProxy
import com.babylone.playbook.core.ext.bindToUi
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), DetailsView {

    companion object {
        private const val POST_ID_EXTRA = "post_id_extra"

        fun newIntent(context: Context, postId: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply { putExtra(POST_ID_EXTRA, postId) }
        }
    }

    @Inject
    lateinit var imageLoader: ImageLoaderProxy

    @Inject
    lateinit var presenter: DetailsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        val postId = intent.getIntExtra(POST_ID_EXTRA, 0)
        presenter.getPostDetails(postId)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }

    override fun renderView(detailsViewState: DetailsViewState) {
        detailsViewState.resource.bindToUi(
            onSuccess = { postDetails ->
                postDetails?.let {
                    activity_details_post_title.text = getString(R.string.post_title, postDetails.title)
                    activity_details_post_body.text = getString(R.string.post_body, postDetails.body)
                    activity_details_author_name.text = getString(R.string.post_author, postDetails.author)
                    activity_details_comments_count.text = getString(R.string.post_comments, postDetails.comments)
                    imageLoader.bind(activity_details_author_avatar, postDetails.avatarUrl)
                }
            },

            onError = {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            },

            onProgress = {}
        )
    }
}
