package com.babylone.playbook.ui.details

import com.babylone.playbook.domain.details.DetailsModel

internal object DetailPostItemFactory {

    private const val AVATAR_URL_PATH = "https://api.adorable.io/avatars/50/"

    fun create(detailsModel: DetailsModel): DetailPostItem = DetailPostItem(
        detailsModel.post.title,
        detailsModel.post.body,
        detailsModel.user.name,
        AVATAR_URL_PATH + detailsModel.user.name,
        detailsModel.comments
    )

}