package com.lucas.matches.api.v1.exception

import java.util.*

class APIError {
    constructor() {}
    constructor(errorMsg: String?) {
        this.errorMsg = errorMsg
    }

    var errorMsg: String? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val apiError = o as APIError
        return errorMsg == apiError.errorMsg
    }

    override fun hashCode(): Int {
        return Objects.hash(errorMsg)
    }

    override fun toString(): String {
        return "APIError{" +
                "errorMsg='" + errorMsg + '\'' +
                '}'
    }
}