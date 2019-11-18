package com.facom.sharehoodapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class AppDatabase private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, AppValues.DB_NAME, null, AppValues.DB_VERSION) {

    init {
        instance = this
    }

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: AppDatabase(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(AppValues.USER_TABLE_NAME, true,
            "id" to TEXT + PRIMARY_KEY + UNIQUE,
            "name" to TEXT,
            "email" to TEXT + UNIQUE,
            "password" to TEXT,
            "cellphone" to TEXT,
            "cpf" to TEXT + UNIQUE
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(AppValues.USER_TABLE_NAME, true)
        onCreate(db)
    }
}

val Context.database: AppDatabase
    get() = AppDatabase.getInstance(this)