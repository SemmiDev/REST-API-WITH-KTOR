package com.sammidev.repository

import com.sammidev.database.StudentTable
import com.sammidev.database.dbQuery
import com.sammidev.model.Student
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DatabaseRespository {

    suspend fun getAllStudent(): HashMap<String, Student> {
        val student = hashMapOf<String, Student>()
        dbQuery {
            StudentTable.selectAll().forEach{
                student[it[StudentTable.id]] = Student(
                    id = it[StudentTable.id],
                    name = it[StudentTable.name],
                    nim = it[StudentTable.nim],
                    major = it[StudentTable.major],
                    semester = it[StudentTable.semester].toInt()
                )
            }
        }
        return student
    }

    suspend fun insertStudent(student: Student): Boolean {
        return dbQuery {
            try {
                StudentTable.insert {
                    it[id] = student.id
                    it[name] = student.name
                    it[nim] = student.nim
                    it[major] = student.major
                    it[semester] = student.semester
                }
                true
            } catch (e: JdbcSQLIntegrityConstraintViolationException) {
                println(e.localizedMessage)
                false
            } catch (e: ExposedSQLException) {
                println(e.localizedMessage)
                false
            }
        }
    }

    suspend fun deleteStudent(id: String): Int {
        return dbQuery {
            StudentTable.deleteWhere { StudentTable.id eq id }
        }
    }

    suspend fun updateStudent(student: Student): Int {
        return dbQuery {
            StudentTable.update({ StudentTable.id eq student.id }) {
                it[name] = student.name
                it[nim] = student.nim
                it[major] = student.major
                it[semester] = student.semester
            }
        }
    }
}