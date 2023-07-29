package stslex.com.data.datasource.mapper

import org.jetbrains.exposed.sql.ResultRow
import stslex.com.data.datasource.UserEntitiesTable.nickname
import stslex.com.data.datasource.UserEntitiesTable.password
import stslex.com.data.datasource.UserEntitiesTable.username
import stslex.com.data.datasource.UserEntitiesTable.uuid
import stslex.com.data.datasource.model.UserEntity

object UserDatasourceMapper {

    fun ResultRow.toData(): UserEntity = UserEntity(
        uuid = this[uuid],
        username = this[username],
        nickname = this[nickname],
        password = this[password]
    )
}