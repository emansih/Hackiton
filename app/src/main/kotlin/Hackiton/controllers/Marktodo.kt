package Hackiton.controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler

class Marktodo: Handler {

    override fun handle(ctx: Context) {
        val todoId = ctx.pathParam("id", String::class.java).get()
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("todo")
        val todoRef = docRef.document(todoId)
        val hashMap: Map<String, Any> = hashMapOf("done" to true)
        todoRef.update(hashMap)
    }
}