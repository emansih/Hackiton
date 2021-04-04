package Hackiton.controllers

import io.javalin.http.Context
import io.javalin.http.Handler

class Rick: Handler {
    override fun handle(ctx: Context) {
        ctx.render("/views/rick.html")
    }
}