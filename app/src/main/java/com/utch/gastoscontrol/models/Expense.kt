package com.utch.gastoscontrol.models

data class Expense(
    var id: Long = 0L,
    var title: String = "",
    var category: String = "",
    var amount: Double = 0.0,
    var dateMillis: Long = System.currentTimeMillis(),
    var note: String = "",
    var paid: Boolean = true
)
