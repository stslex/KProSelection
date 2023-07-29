package stslex.com.datasources.user.table

import org.jetbrains.exposed.sql.Table

object UserEntitiesTable : Table() {
    val uuid = integer("uuid").autoIncrement() // TODO replace with UUID generator
    val username = varchar("username", 128)
    val password = varchar("password", 128)
    val nickname = varchar("nickname", 128)

    override val primaryKey: PrimaryKey = PrimaryKey(uuid)
}