package com.facom.sharehoodapp.service

import com.facom.sharehoodapp.AppValues
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import kotlinx.coroutines.Deferred
import okhttp3.Response

class RequestService {

    companion object {
        fun findAll(): Deferred<Response> {
            return httpGetAsync {
                host = AppValues.API_HOST
                path = "/requests"
            }
        }
        fun create(request: Request): Deferred<Response> {
            return httpPostAsync {
                host = AppValues.API_HOST
                path = "/requests"
                body {
                    json {
                        "name" to request.name
                        "duration" to request.duration
                        "reason" to request.reason
                        "user" to request.user                    }
                }
            }
        }
    }

}