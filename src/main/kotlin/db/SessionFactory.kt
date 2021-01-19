package db

import org.jetbrains.exposed.sql.Database

class SessionFactory {
    companion object {
        fun getSession() = Database.connect(
            "jdbc:mysql://localhost:3306/test",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = "123456"
        )
    }
}