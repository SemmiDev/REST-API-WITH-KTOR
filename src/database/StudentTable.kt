package com.sammidev.database

import org.jetbrains.exposed.sql.Table

object StudentTable : Table() {
    val id = varchar("id", 255).primaryKey()
    val name = varchar("name", 255)
    val nim = varchar("nim", 255)
    val major = varchar("major", 255)
    val semester = integer("semester")
}