package Hackiton.controllers

import Hackiton.models.CalendarItems
import Hackiton.models.TodoItems
import com.google.firebase.cloud.FirestoreClient
import io.javalin.http.Context
import io.javalin.http.Handler
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


class Dashboard: Handler {

    private val calendarArray = arrayListOf<CalendarItems>()
    private val todoArray = arrayListOf<TodoItems>()
    private val hashMap: HashMap<String, Any> = HashMap()

    override fun handle(ctx: Context) {
        val userId = ctx.cookie("userId") ?: ""
        val db = FirestoreClient.getFirestore()
        calendarArray.clear()
        todoArray.clear()
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

        db.collection("todo").listDocuments().forEach {  docRef ->
            val todoItems = docRef.get().get().toObject(TodoItems::class.java)
            if(todoItems?.userId != null && todoItems.userId.contentEquals(userId)){
                todoArray.add(TodoItems(
                        userId, todoItems.todoItemId, todoItems.todoDescription,
                        todoItems.todoDate, todoItems.done
                ))
            }
        }
        val rnds = (1..10).random()
        val localDate = LocalDate.now(ZoneId.of("Australia/Melbourne"))
        val parsedDate = localDate.dayOfMonth.toString() + " " + localDate.month + " " + localDate.year
        hashMap["date"] = parsedDate
        db.collection("quotes").listDocuments().forEach {  docRef ->
            hashMap["quotes"] = docRef.get().get().get(rnds.toString()) as String
        }
        hashMap["calendarEntries"] = calendarArray
        hashMap["todoEntries"] = todoArray
        ctx.render("/views/dashboard.html", hashMap)
    }

}