package com.sammidev.model

data class StudentResponse(
    val status: String,
    val message: String,
    val entity: Any? = null
)