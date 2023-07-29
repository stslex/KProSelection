package stslex.com.data.datasource

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import stslex.com.data.datasource.DatabaseFactory.dbQuery
import stslex.com.data.datasource.mapper.UserDatasourceMapper.toData
import stslex.com.data.datasource.model.UserEntity
import stslex.com.data.repository.model.UserAuthModel

class UserDataSourceImpl : UserDataSource {

    override suspend fun isUserExist(
        username: String
    ): Boolean = dbQuery {
        val user = UserEntitiesTable
            .select {
                UserEntitiesTable.username eq username
            }
            .firstOrNull()
        user != null
    }

    override suspend fun saveUser(
        user: UserAuthModel
    ): UserEntity? = dbQuery {
        val insertStatement = UserEntitiesTable.insert {
            it[username] = user.username
            it[password] = user.password
            it[nickname] = ""
        }
        insertStatement
            .resultedValues
            ?.singleOrNull()
            ?.toData()
    }

    override suspend fun getUser(
        username: String
    ): UserEntity? = dbQuery {
        UserEntitiesTable
            .select {
                UserEntitiesTable.username eq username
            }
            .firstOrNull()
            ?.toData()
    }
}