package logic

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
//https://github.com/JetBrains/Exposed/wiki/Getting-Started#download

    val connect = SessionFactory.getSession()

    transaction(connect) {
        // print sql to std-out
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(City)

        val place1 = WorldGame.new {
            name = "Madrid"
            country = "Spain"
        }
        val place2 = WorldGame.new {
            name = "Paris"
            country = "France"
        }

        println(WorldGame.all())
        println(WorldGame.find { City.country eq "France" })

        place1.name = "Sevilla"

        // 'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
        println("Cities: ${City.selectAll()}")

        SchemaUtils.create(MoneyDao)

        SnackMachineDAO.new {
            oneCentCount = 1
            tenCentCount = 0
            quarterCentCount = 1
            oneDollarCount = 1
            fiveDollarCount = 1
            twentyDollarCount = 1
        }

        //SAVE THE SnackMachineDAO
        println(SnackMachineDAO.all())
    }
}

object City : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
    val country: Column<String> = varchar("country", 50)
}

class WorldGame(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorldGame>(City)

    var name by City.name
    var country by City.country
}

///
class SnackMachineDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SnackMachineDAO>(MoneyDao)

    var oneCentCount by MoneyDao.oneCentCount
    var tenCentCount by MoneyDao.tenCentCount
    var quarterCentCount by MoneyDao.quarterCentCount
    var oneDollarCount by MoneyDao.oneDollarCount
    var fiveDollarCount by MoneyDao.fiveDollarCount
    var twentyDollarCount by MoneyDao.twentyDollarCount
}

object MoneyDao : IntIdTable() {
    val oneCentCount: Column<Int> = integer("oneCentCount")
    val tenCentCount: Column<Int> = integer("tenCentCount")
    val quarterCentCount: Column<Int> = integer("quarterCentCount")
    val oneDollarCount: Column<Int> = integer("oneDollarCount")
    val fiveDollarCount: Column<Int> = integer("fiveDollarCount")
    val twentyDollarCount: Column<Int> = integer("twentyDollarCount")
}