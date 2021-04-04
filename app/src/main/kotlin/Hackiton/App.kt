package Hackiton

import Hackiton.controllers.*
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.javalin.Javalin
import io.javalin.core.JavalinConfig
import io.javalin.http.staticfiles.Location

fun main(args: Array<String>){
    val app = Javalin.create { obj: JavalinConfig ->
        obj.enableDevLogging()
        obj.addStaticFiles("/scripts", Location.CLASSPATH)
        obj.addStaticFiles("/css", Location.CLASSPATH)
        obj.addStaticFiles("/images", Location.CLASSPATH)
    }.start(8080)
    val serviceAccount = Dashboard::class.java.getResourceAsStream("/credentials.json")
    val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
    FirebaseApp.initializeApp(options)
    configureRoutes(app)
}

 private fun configureRoutes(app: Javalin) {
     app.get("/", Authentication())
     app.get("/login", Authentication())
     app.get("/dashboard", Dashboard())
     app.get("/addEvent", AddEvent())
     app.post("/storeEvent", StoreEvent())
     app.get("/todo", TodoController())
     app.post("/storeGoals", StoreGoal())
     app.get("/marktodo/:id", Marktodo())
     app.get("/timer", Timer())
     app.error(404, Rick())

 }
