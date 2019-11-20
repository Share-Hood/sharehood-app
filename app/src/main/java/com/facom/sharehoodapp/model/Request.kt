package com.facom.sharehoodapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    @SerialName("_id") val id: String,
    val name: String,
    val duration: Int,
    val reason: String,
    val user: User,
    @SerialName("__v") val v: String
) : java.io.Serializable