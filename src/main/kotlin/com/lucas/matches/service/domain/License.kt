package com.lucas.matches.service.domain

import java.util.*

class License {
    var licenseType: LicenseType? = null
    var id: String? = null

    constructor() {}
    constructor(licenseType: LicenseType?, id: String?) {
        this.licenseType = licenseType
        this.id = id
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val license = o as License
        return licenseType == license.licenseType && id == license.id
    }

    override fun hashCode(): Int {
        return Objects.hash(licenseType, id)
    }

    override fun toString(): String {
        return "License{" +
                "licenseType=" + licenseType +
                ", id='" + id + '\'' +
                '}'
    }
}