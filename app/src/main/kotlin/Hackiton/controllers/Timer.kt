package Hackiton.controllers

import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import java.time.LocalDate
import java.time.ZoneId

class Timer: Handler {

    private val hashMap: HashMap<String, Any> = HashMap()


    override fun handle(ctx: Context) {
        val db = FirestoreClient.getFirestore()
        val rnds = (1..10).random()
        db.collection("quotes").listDocuments().forEach {  docRef ->
            hashMap["quotes"] = docRef.get().get().get(rnds.toString()) as String
        }
        val localDate = LocalDate.now(ZoneId.of("Australia/Melbourne"))
        val parsedDate = localDate.dayOfMonth.toString() + " " + localDate.month + " " + localDate.year
        hashMap["date"] = parsedDate

        ctx.render("/views/timer.html", hashMap)
    }
}
