package com.facom.sharehoodapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lending(
    @SerialName("_id") val id: String,
    val createdDate: String, val finalizedDate: String,
    val request: Request,
    val lender: User,
    val borrower: User,
    @SerialName("__v") val v: String
) : java.io.Serializable