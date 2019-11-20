package com.facom.sharehoodapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class User: java.io.Serializable {

    @SerialName("_id")
    var id = ""
    var name = ""
    var email = ""
    var password = ""
    var cellphone = ""
    var cpf = ""
    @SerialName("__v")
    var v = ""

    constructor()

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    override fun toString(): String {
        return "User(name='$name', email='$email')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (cellphone != other.cellphone) return false
        if (cpf != other.cpf) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + cellphone.hashCode()
        result = 31 * result + cpf.hashCode()
        return result
    }


}