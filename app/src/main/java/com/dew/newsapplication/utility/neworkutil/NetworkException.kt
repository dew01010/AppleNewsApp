package com.msewa.healthism_merchant.util

import java.io.IOException


class NetworkException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}