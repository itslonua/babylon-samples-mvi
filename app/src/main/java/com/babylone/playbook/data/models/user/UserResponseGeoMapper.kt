package com.babylone.playbook.data.models.user

import com.babylone.playbook.domain.user.UserGeo

object UserResponseGeoMapper {

    fun fromNetwork(geo: UserResponseGeo): UserGeo {
        return UserGeo(geo.lat, geo.long)
    }

}