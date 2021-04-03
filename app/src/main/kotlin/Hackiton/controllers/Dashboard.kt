package Hackiton.controllers

import Hackiton.models.CalendarItems
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.*
import java.util.*
import kotlin.collections.ArrayList

class Dashboard: Handler {

    private val calendarArray = arrayListOf<CalendarItems>()

    override fun handle(ctx: Context) {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val JSON_FACTORY = JacksonFactory.getDefaultInstance()
        val credential = getCredentials()
        val service = Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Hackiethon 2021")
                .build()
        val now = DateTime(System.currentTimeMillis())
        val zonedDateTime =  ZonedDateTime.ofInstant(Instant.now(), ZoneId.of( "Australia/Melbourne"))
        val endZoneDateTime = zonedDateTime.toLocalDate()
                .plusDays(1)
                .atStartOfDay()
                .minusMinutes(1)
                .toEpochSecond(ZoneId.of("Australia/Melbourne").rules.getOffset(Instant.now()))
                .times(1000)
        val endOfDay = DateTime(endZoneDateTime)
        val events = service.events().list("primary")
                .setMaxResults(200)
                .setTimeMin(now)
                .setTimeMax(endOfDay)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute()
        val items = events.items
        if (items.isNotEmpty()) {
            for (event in items) {
                var end = event.end.dateTime
                val start: String = if (event.start.dateTime == null) {
                    "All Day"
                } else {
                    val dateTime = LocalDateTime.parse(event.start.dateTime.toStringRfc3339())
                    dateTime.hour.toString() + ":" + dateTime.minute
                }
                if(end == null){
                    end = event.end.date
                }
                calendarArray.add(CalendarItems(event.summary, start))
            }
        }
        val hashMap: HashMap<String, ArrayList<CalendarItems>> = HashMap()
        hashMap["calendarEntries"] = calendarArray
        ctx.render("/views/dashboard.html", hashMap)
    }

    private fun getCredentials(): Credential {
        val input = Authentication::class.java.getResourceAsStream("/credentials.json")
        if(input != null){
            val clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), InputStreamReader(input))
            val flow = GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientSecrets, Collections.singletonList(CalendarScopes.CALENDAR))
                    .setDataStoreFactory(FileDataStoreFactory(File("tokens")))
                    .setAccessType("offline")
                    .build()
            val receiver = LocalServerReceiver.Builder().setPort(8888).build()
            return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
        } else {
            throw FileNotFoundException("Resource not found")
        }
    }

}