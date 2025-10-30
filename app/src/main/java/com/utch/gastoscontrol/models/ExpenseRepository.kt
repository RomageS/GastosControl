package com.utch.gastoscontrol.models

import android.content.ContentValues
import android.content.Context

class ExpenseRepository(ctx: Context) {
    private val helper = DatabaseHelper(ctx)

    fun insert(e: Expense): Long {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(DatabaseHelper.C_TITLE, e.title)
            put(DatabaseHelper.C_CATEGORY, e.category)
            put(DatabaseHelper.C_AMOUNT, e.amount)
            put(DatabaseHelper.C_DATE, e.dateMillis)
            put(DatabaseHelper.C_NOTE, e.note)
            put(DatabaseHelper.C_PAID, if (e.paid) 1 else 0)
        }
        val id = db.insert(DatabaseHelper.T_EXPENSES, null, cv)
        db.close()
        return id
    }

    fun update(e: Expense): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(DatabaseHelper.C_TITLE, e.title)
            put(DatabaseHelper.C_CATEGORY, e.category)
            put(DatabaseHelper.C_AMOUNT, e.amount)
            put(DatabaseHelper.C_DATE, e.dateMillis)
            put(DatabaseHelper.C_NOTE, e.note)
            put(DatabaseHelper.C_PAID, if (e.paid) 1 else 0)
        }
        val rows = db.update(DatabaseHelper.T_EXPENSES, cv, "id=?", arrayOf(e.id.toString()))
        db.close()
        return rows > 0
    }

    fun delete(id: Long): Boolean {
        val db = helper.writableDatabase
        val rows = db.delete(DatabaseHelper.T_EXPENSES, "id=?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }

    fun findById(id: Long): Expense? {
        val db = helper.readableDatabase
        val c = db.query(DatabaseHelper.T_EXPENSES, null, "id=?", arrayOf(id.toString()), null, null, null)
        val e = if (c.moveToFirst()) map(c) else null
        c.close(); db.close()
        return e
    }

    fun findAll(): List<Expense> {
        val db = helper.readableDatabase
        val c = db.query(DatabaseHelper.T_EXPENSES, null, null, null, null, null, "${DatabaseHelper.C_DATE} DESC")
        val list = buildList { while (c.moveToNext()) add(map(c)) }
        c.close(); db.close()
        return list
    }

    fun totalAll(): Double {
        val db = helper.readableDatabase
        val c = db.rawQuery("SELECT SUM(${DatabaseHelper.C_AMOUNT}) FROM ${DatabaseHelper.T_EXPENSES}", null)
        val total = if (c.moveToFirst()) if (c.isNull(0)) 0.0 else c.getDouble(0) else 0.0
        c.close(); db.close()
        return total
    }

    private fun map(c: android.database.Cursor) = Expense(
        id = c.getLong(c.getColumnIndexOrThrow(DatabaseHelper.C_ID)),
        title = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.C_TITLE)),
        category = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.C_CATEGORY)),
        amount = c.getDouble(c.getColumnIndexOrThrow(DatabaseHelper.C_AMOUNT)),
        dateMillis = c.getLong(c.getColumnIndexOrThrow(DatabaseHelper.C_DATE)),
        note = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.C_NOTE)),
        paid = c.getInt(c.getColumnIndexOrThrow(DatabaseHelper.C_PAID)) == 1
    )
}
