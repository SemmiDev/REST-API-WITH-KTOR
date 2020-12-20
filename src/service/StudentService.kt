package com.sammidev.service

import com.sammidev.model.Student
import com.sammidev.model.StudentResponse
import com.sammidev.repository.DatabaseRespository
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.widget(databaseRespository: DatabaseRespository) {
    route("/student") {
        get {
            val result = databaseRespository.getAllStudent()
            when {
                result.isEmpty() -> {
                    call.respond(StudentResponse("success", "Data Student kosong"))
                }
                else -> {
                    call.respond(StudentResponse("success", "Jumlah Student ${result.size}", result))
                }
            }
        }
        get("/delete") {
            val id = call.request.queryParameters["id"] ?: "0"
            val result = databaseRespository.deleteStudent(id)
            when {
                result > 0 -> {
                    call.respond(StudentResponse("success", "data dengan id $id berhasil dihapus"))
                }
                else -> {
                    call.respond(StudentResponse("failed", "tidak dapat menghapus data"))
                }
            }
        }
        get("/insert") {
            val name = call.request.queryParameters["name"] ?: "sammi aldhi yanto"
            val nim = call.request.queryParameters["nim"] ?: "2003113948"
            val major = call.request.queryParameters["major"] ?: "CS"
            val semester = call.request.queryParameters["semester"] ?: "1"
            val id = call.request.queryParameters["id"] ?: "1"

            val student = Student(
                name = name,
                nim = nim,
                major = major,
                semester = semester.toInt(),
                id = id)

            val result = databaseRespository.insertStudent(student)
            when {
                result -> {
                    call.respond(StudentResponse("success", "${student.name} berhasil ditambahkan", student))
                }
                else -> {
                    call.respond(StudentResponse("failed", "${student.name} tidak dapat ditambahkan"))
                }
            }
        }
        get("/update") {
            val name = call.request.queryParameters["name"] ?: "sammi aldhi yanto"
            val nim = call.request.queryParameters["nim"] ?: "2003113948"
            val major = call.request.queryParameters["major"] ?: "CS"
            val semester = call.request.queryParameters["semester"] ?: "1"
            val id = call.request.queryParameters["id"] ?: "1"

            val student = Student(
                name = name,
                nim = nim,
                major = major,
                semester = semester.toInt(),
                id = id)

            val result = databaseRespository.updateStudent(student)

            when {
                result > 0 -> {
                    call.respond(
                        StudentResponse(
                            "success","data dengan id ${student.id} berhasil diupdate", student
                        )
                    )
                }
                else -> {
                    call.respond(StudentResponse("failed", "tidak dapat melakukan update data"))
                }
            }
        }
    }
}