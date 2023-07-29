package stslex.com.datasources.user.model

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import stslex.com.datasources.user.table.UserEntitiesTable.nickname
import stslex.com.datasources.user.table.UserEntitiesTable.password
import stslex.com.datasources.user.table.UserEntitiesTable.username
import stslex.com.datasources.user.table.UserEntitiesTable.uuid

object UserDatasourceMapper {

    fun Query.toData(): List<UserEntity> = map { it.toData() }

    fun ResultRow.toData(): UserEntity = UserEntity(
        uuid = this[uuid].toString(),
        username = this[username],
        nickname = this[nickname],
        password = this[password]
    )
}