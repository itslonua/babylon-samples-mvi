package com.babylone.playbook.data.models.user

import com.babylone.playbook.domain.user.UserAddress


object UserResponseAddressMapper {

    fun fromNetwork(address: UserResponseAddress): UserAddress {
        return UserAddress(
            address.street,
            address.suite,
            address.city,
            address.zipcode,
            UserResponseGeoMapper.fromNetwork(address.geo)
        )
    }

}
