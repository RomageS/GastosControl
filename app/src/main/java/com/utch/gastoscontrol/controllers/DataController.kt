package com.utch.gastoscontrol.controllers

import android.content.Context
import com.utch.gastoscontrol.models.Expense
import com.utch.gastoscontrol.models.ExpenseRepository

class DataController(ctx: Context) {
    private val repo = ExpenseRepository(ctx)
    fun create(e: Expense) = repo.insert(e)
    fun update(e: Expense) = repo.update(e)
    fun delete(id: Long) = repo.delete(id)
    fun all() = repo.findAll()
    fun get(id: Long) = repo.findById(id)
    fun total() = repo.totalAll()
}
