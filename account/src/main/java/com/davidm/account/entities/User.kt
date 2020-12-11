package com.davidm.account.entities

data class User(
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val title: String
)