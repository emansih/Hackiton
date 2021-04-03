package Hackiton.controllers

import Hackiton.models.CalendarItems
import io.javalin.http.Context
import io.javalin.http.Handler

class Dashboard: Handler {

    private val calendarArray = arrayListOf<CalendarItems>()

    override fun handle(ctx: Context) {

        ctx.render("/views/dashboard.html")
    }


}