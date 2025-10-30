package com.utch.gastoscontrol.controllers

import android.content.Context
import android.widget.EditText
import android.widget.Toast

class ValidationController(private val ctx: Context) {
    fun require(et: EditText, field: String): Boolean {
        val s = et.text.toString().trim()
        if (s.isEmpty()) {
            et.error = "$field es requerido"
            Toast.makeText(ctx, "$field es requerido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    fun positive(et: EditText, field: String): Boolean {
        return try {
            val v = et.text.toString().toDouble()
            if (v <= 0) throw NumberFormatException()
            true
        } catch (_: Exception) {
            et.error = "$field debe ser positivo"
            Toast.makeText(ctx, "$field debe ser positivo", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
