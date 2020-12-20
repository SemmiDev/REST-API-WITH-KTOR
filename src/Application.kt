package com.sammidev

import com.sammidev.database.DatabaseConfiguration
import com.sammidev.database.StudentTable
import com.sammidev.repository.DatabaseRespository
import com.sammidev.service.widget
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.gson.*
import io.ktor.features.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    Database.connect(DatabaseConfiguration.hikariConfigDataSource())
    transaction {
        create(StudentTable)
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {
        widget(DatabaseRespository())
    }
}