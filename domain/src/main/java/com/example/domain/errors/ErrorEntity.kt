package com.example.domain.errors

data class ErrorEntity (
    val propertyPath: String?,
    val message: String?
)