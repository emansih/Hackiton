package Hackiton

import Hackiton.controllers.Authentication
import Hackiton.controllers.Dashboard
import io.javalin.Javalin
import io.javalin.core.JavalinConfig
import io.javalin.http.staticfiles.Location

fun main(args: Array<String>){
    val app = Javalin.create { obj: JavalinConfig ->
        obj.enableDevLogging()
        obj.addStaticFiles("/scripts", Location.CLASSPATH)
        obj.addStaticFiles("/css", Location.CLASSPATH)
    }.start(8080)
    configureRoutes(app)
}

 private fun configureRoutes(app: Javalin) {
     app.get("/login", Authentication())
     app.get("/dashboard", Dashboard())
 }

