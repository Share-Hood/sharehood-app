package com.facom.sharehoodapp.dao

import android.app.Application
import android.content.Context
import com.facom.sharehoodapp.AppValues
import com.facom.sharehoodapp.database
import com.facom.sharehoodapp.model.User
import org.jetbrains.anko.db.select

class UserDao{

    companion object {

        fun getLoggedUser(context: Context): User? {
            var loggedUser: User? = null
            context.database.use {
                select(AppValues.USER_TABLE_NAME).exec {
                    if(moveToNext()) {
                        loggedUser = User()
                        loggedUser?.id = getString(0)
                        loggedUser?.name = getString(1)
                        loggedUser?.email = getString(2)
                        loggedUser?.password = getString(3)
                        loggedUser?.cellphone = getString(4)
                        loggedUser?.cpf = getString(5)
                    }
                }
            }
            return loggedUser
        }
    }

}