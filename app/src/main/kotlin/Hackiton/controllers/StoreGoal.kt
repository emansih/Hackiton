package Hackiton.controllers

import Hackiton.models.TodoItems
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler

class StoreGoal: Handler {
    override fun handle(ctx: Context) {
        val date: String = ctx.formParam("date") ?: ""
        val description: String = ctx.formParam("description") ?: ""
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("todo")
        val todoRef = docRef.document()
        todoRef.create(TodoItems(ctx.cookie("userId") ?: "", todoRef.get().get().id,
                description, date, false))
        ctx.redirect("/dashboard")
    }
}