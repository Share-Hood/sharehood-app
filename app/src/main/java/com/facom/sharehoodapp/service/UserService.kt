package com.facom.sharehoodapp.service

import com.facom.sharehoodapp.AppValues
import com.facom.sharehoodapp.model.User
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import kotlinx.coroutines.Deferred
import okhttp3.Response

class UserService {

    companion object {
        fun login(user: User): Deferred<Response> {
            return httpPostAsync {
                host = "sharehood-api.herokuapp.com"
                path = "/login"
                body {
                    json {
                        "email" to user.email
                        "password" to user.password
                    }
                }
            }
        }
        fun cadastroUsuario(user: User): Deferred<Response> {
            return httpPostAsync {
                host = "sharehood-api.herokuapp.com"
                path = "/users"
                body {
                    json {
                        "name" to user.name
                        "email" to user.email
                        "password" to user.password
                    }
                }
            }
        }
        fun esqueciMinhaSenha(email: String): Deferred<Response> {
            return httpPostAsync {
                host = "sharehood-api.herokuapp.com"
                path = "/forgot-password"
                body {
                    json {
                        "email" to email
                    }
                }
            }
        }
    }

}