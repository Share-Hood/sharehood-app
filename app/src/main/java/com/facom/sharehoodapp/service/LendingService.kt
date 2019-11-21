package com.facom.sharehoodapp.service

import com.facom.sharehoodapp.AppValues
import com.facom.sharehoodapp.model.Lending
import com.facom.sharehoodapp.model.Request
import com.facom.sharehoodapp.model.User
import io.github.rybalkinsd.kohttp.dsl.async.httpGetAsync
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import kotlinx.coroutines.Deferred
import okhttp3.Response

class LendingService {

    companion object {
        fun findAll(user: User): Deferred<Response> {
            return httpGetAsync {
                host = AppValues.API_HOST
                path = "/lendings/${user.id}"
            }
        }
        fun findAllLended(user: User): Deferred<Response> {
            return httpGetAsync {
                host = AppValues.API_HOST
                path = "/lendings-lender/${user.id}"
            }
        }
        fun findAllBorrowed(user: User): Deferred<Response> {
            return httpGetAsync {
                host = AppValues.API_HOST
                path = "/lendings-borrower/${user.id}"
            }
        }
        fun create(lending: Lending): Deferred<Response> {
            return httpPostAsync {
                host = AppValues.API_HOST
                path = "/lendings"
                body {
                    json {
                        "request" to lending.request.id
                        "lender" to lending.lender.id
                        "borrower" to lending.borrower.id
                    }
                }
            }
        }
    }

}