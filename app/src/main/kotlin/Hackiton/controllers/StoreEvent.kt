package Hackiton.controllers

import Hackiton.models.CalendarItems
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import java.time.LocalDateTime
import java.time.ZoneId

class StoreEvent: Handler {
    override fun handle(ctx: Context) {
        val startTime: String = ctx.formParam("startTime") ?: ""
        val startDate: String = ctx.formParam("startDate") ?: ""
        val description: String = ctx.formParam("description") ?: ""
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection("calendar")
        val calendarRef = docRef.document()
        val zone = ZoneId.of("Australia/Melbourne")
        val zoneOffSet = zone.rules.getOffset(LocalDateTime.now())
        val epochSecond = LocalDateTime.parse(startDate + "T" + startTime).toEpochSecond(zoneOffSet)
        calendarRef.create(CalendarItems(ctx.cookie("userId") ?: "", calendarRef.get().get().id,
                description, epochSecond.toString()))
        ctx.redirect("/dashboard")

    }
}