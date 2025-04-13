package com.rest_api.spring.exceptions

import java.util.Date

class ExceptionResponse(
    val timestamp: Date,
    val message: String?,
    val details: String?
)