package com.lucas.matches.service.util

import com.lucas.matches.service.domain.License
import com.lucas.matches.service.domain.LicenseType
import com.lucas.matches.service.exception.InvalidLicenseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class LicenseParser {
    private val logger: Logger = LoggerFactory.getLogger(LicenseParser::class.java)
    fun parse(licenseStringList: List<String>): List<License> {
        val licenses: MutableList<License> = ArrayList()
        licenseStringList.forEach(Consumer { licenseString: String -> licenses.add(parse(licenseString)) })
        return licenses
    }

    private fun parse(licenseString: String): License {
        val licenseArray = licenseString.split("-").toTypedArray()
        if (licenseArray.size < 2) {
            logger.error("Invalid license: $licenseString")
            throw InvalidLicenseException("Invalid license.")
        }
        val license = License(LicenseType.Companion.value(licenseArray[0]), licenseArray[1])
        if (license.licenseType == null || license.id == null || license.id!!.isEmpty()) {
            logger.error("Invalid license: $licenseString")
            throw InvalidLicenseException("Invalid license.")
        }
        return license
    }

}