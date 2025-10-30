package com.utch.gastoscontrol.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(ctx: Context) :
    SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $T_EXPENSES (
                $C_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $C_TITLE TEXT NOT NULL,
                $C_CATEGORY TEXT,
                $C_AMOUNT REAL NOT NULL,
                $C_DATE INTEGER NOT NULL,
                $C_NOTE TEXT,
                $C_PAID INTEGER NOT NULL DEFAULT 1
            );
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS $T_EXPENSES")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "expenses.db"
        const val DB_VERSION = 1
        const val T_EXPENSES = "expenses"
        const val C_ID = "id"
        const val C_TITLE = "title"
        const val C_CATEGORY = "category"
        const val C_AMOUNT = "amount"
        const val C_DATE = "date_millis"
        const val C_NOTE = "note"
        const val C_PAID = "paid"
    }
}
