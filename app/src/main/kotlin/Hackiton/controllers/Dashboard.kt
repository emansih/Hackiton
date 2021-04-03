package Hackiton.controllers

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*

class Dashboard: Handler {

    override fun handle(ctx: Context) {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val JSON_FACTORY = JacksonFactory.getDefaultInstance()
        Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName("Hackiethon 2021")
                .build()
        ctx.render("/views/dashboard.html")
    }

    fun getCredentials(): Credential {
        val input = Authentication::class.java.getResourceAsStream("/credentials.json")
        if(input != null){
            val clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), InputStreamReader(input))
            val flow = GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientSecrets, Collections.singletonList(CalendarScopes.CALENDAR_READONLY))
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