package stslex.com.datasources.user.table

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun initDatabase() {
        val config = HoconApplicationConfig(ConfigFactory.load())
        val db = Database.connect(
            driver = "org.postgresql.Driver",
            url = config.property("postgres.url").getString(),
            user = config.property("postgres.user").getString(),
            password = config.property("postgres.password").getString(),
        )
        transaction(db) {
            SchemaUtils.create(UserEntitiesTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}