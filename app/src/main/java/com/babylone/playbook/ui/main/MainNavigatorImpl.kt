package com.babylone.playbook.ui.main

import com.babylone.playbook.ui.details.DetailsActivity

class MainNavigatorImpl(val context: MainActivity) : MainNavigator {

    override fun showPostDetails(postId: Int) {
        context.startActivity(DetailsActivity.newIntent(context, postId))
    }

}