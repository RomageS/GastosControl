package com.utch.gastoscontrol.controllers

import android.content.Context
class MainController(ctx: Context) {
    private val data = DataController(ctx)
    fun load() = data.all()
    fun total() = data.total()
}
