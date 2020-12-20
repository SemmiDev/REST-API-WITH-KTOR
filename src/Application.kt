package com.sammidev

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    val client = HttpClient(Jetty) {
    }

    val todolist = ArrayList<ToDo>()
    val jsonRespond = """{
        "id": 1,
        "name": "NGODING",
        "desc": "NOTHING",
        "done": false
    """

    routing {
        route("/todo") {
            get {
                call.respond(todolist)
            }
            get("/{id}") {
                call.respond(todolist[call.parameters["id"]!!.toLong().toInt()])
            }
            post {
                var newToDo = call.receive<ToDo>()
                newToDo.id = todolist.size
                todolist.add(newToDo)
                call.respond("added")
            }
        }
        get("/samples") {
            call.respond(
                listOf(
                    ToDo(1,"Sammi Aldhi Yanto","nothing",true),
                    ToDo(2,"Aditya Andika Putra","nothing",false),
                    ToDo(3,"Ayatullah Ramadhan Jacoeb","nothing",false)
                )
            )
        }

        get("/ping/text") {
            call.respondText("Pong!", contentType = ContentType.Text.Plain)
        }
        get("/ping/json") {
            call.respond(mapOf(
                "name" to "sammidev",
                "nim" to "2003113948",
                "class" to "A",
                "semester" to 1,
                "major" to "Computer Science"
            ))
        }
        get("/api/student") {
            val name = call.request.queryParameters["name"]
            val nim = call.request.queryParameters["nim"]
            if (!name.isNullOrBlank() && !nim.isNullOrEmpty()) {
                call.respond(
                    listOf(
                        mapOf(
                            "name" to name,
                            "nim" to nim,
                            "uninversitas" to "Riau",
                        ),
                        mapOf(
                            "name" to name+"2",
                            "nim" to nim+"2",
                            "uninversitas" to "Riau",
                        )
                    )
                )
            }else {
                call.respond(
                    mapOf(
                        "error messages" to "name and nim must not null"
                    )
                )
            }
        }
    }
}


data class Student(
    val id: Long,
    val name: String,
    val nim: String,
    val major: String
)
data class ToDo(
    var id: Int,
    val name: String,
    val desc: String,
    val done: Boolean
)