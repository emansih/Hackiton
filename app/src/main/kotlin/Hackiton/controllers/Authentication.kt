package Hackiton.controllers

import io.javalin.http.Context
import io.javalin.http.Handler

class Authentication: Handler {
    override fun handle(ctx: Context) {
        val userId = ctx.cookieStore("userId") as String?
        if(userId.isNullOrEmpty()){
            ctx.render("/views/login.html")
        }
    }
}