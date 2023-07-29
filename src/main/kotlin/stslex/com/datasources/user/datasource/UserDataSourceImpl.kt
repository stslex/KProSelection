package stslex.com.datasources.user.datasource

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import stslex.com.datasources.user.model.UserDatasourceMapper.toData
import stslex.com.datasources.user.model.UserEntity
import stslex.com.datasources.user.table.DatabaseFactory.dbQuery
import stslex.com.datasources.user.table.UserEntitiesTable
import stslex.com.features.auth.domain.model.UserAuthModel

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

    override suspend fun getAll(): List<UserEntity> = dbQuery {
        UserEntitiesTable
            .selectAll()
            .limit(20)
            .toData()
    }

    override suspend fun clearAll(): Unit = dbQuery {
        UserEntitiesTable.deleteAll()
    }

    override suspend fun deleteUser(
        username: String
    ): Unit = dbQuery {
        UserEntitiesTable.deleteWhere {
            UserEntitiesTable.username eq username
        }
    }
}