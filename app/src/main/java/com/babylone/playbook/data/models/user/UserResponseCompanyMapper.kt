package com.babylone.playbook.data.models.user

import com.babylone.playbook.domain.user.UserCompany

object UserResponseCompanyMapper {

    fun fromNetwork(company: UserResponseCompany): UserCompany {
        return UserCompany(company.name, company.catchPhrase, company.bs)
    }

}