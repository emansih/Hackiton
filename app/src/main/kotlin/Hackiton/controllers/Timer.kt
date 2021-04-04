package Hackiton.controllers

import io.javalin.http.Context
import io.javalin.http.Handler

class Timer: Handler {
    override fun handle(ctx: Context) {
        ctx.render("/views/timer.html")
    }
}
