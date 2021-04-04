package Hackiton.controllers

import Hackiton.models.CalendarItems
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


class Dashboard: Handler {

    private val calendarArray = arrayListOf<CalendarItems>()

    override fun handle(ctx: Context) {
        val userId = ctx.cookie("userId") ?: ""
        val db = FirestoreClient.getFirestore()
        calendarArray.clear()
        db.collection("calendar").listDocuments().forEach {  docRef ->
            val calendar = docRef.get().get().toObject(CalendarItems::class.java)
            if(calendar?.userId != null && calendar.userId.contentEquals(userId)){
                val localDate = LocalDateTime.now().atZone(ZoneId.of("Australia/Melbourne")).toLocalDate()
                val startDate = Instant.ofEpochSecond(calendar.startDateTime.toLong()).atZone(ZoneId.of("Australia/Melbourne")).toLocalDate()
                if(localDate.isEqual(startDate)){
                    val startDateTime = Instant.ofEpochSecond(calendar.startDateTime.toLong()).atZone(ZoneId.of("Australia/Melbourne")).toLocalDateTime()
                    calendarArray.add(CalendarItems(userId, calendar.calendarId, calendar.description,
                            startDateTime.hour.toString() + ":" + startDateTime.minute))
                }
            }
        }
        val rnds = (1..10).random()
        val hashMap: HashMap<String, Any> = HashMap()
        val localDate = LocalDate.now(ZoneId.of("Australia/Melbourne"))
        val parsedDate = localDate.dayOfMonth.toString() + " " + localDate.month + " " + localDate.year
        hashMap["date"] = parsedDate
        db.collection("quotes").listDocuments().forEach {  docRef ->
            hashMap["quotes"] = docRef.get().get().get(rnds.toString()) as String
        }
        hashMap["calendarEntries"] = calendarArray
        ctx.render("/views/dashboard.html", hashMap)
    }



    // TO Add
    /*
      val db = FirestoreClient.getFirestore()
        val docRef = db.collection("calendar")
        val calendarRef = docRef.document()
        calendarRef.create(CalendarItems(userId, calendarRef.get().get().id,"This is another test", "1617495192"))
     */
}