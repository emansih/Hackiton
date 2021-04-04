package Hackiton.controllers

import io.javalin.http.Context
import io.javalin.http.Handler

class AddEvent: Handler {

    override fun handle(ctx: Context) {
        ctx.render("/views/addEvent.html")
    }
}