package stslex.com.datasources.user.datasource

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import stslex.com.datasources.user.model.UserDatasourceMapper.toData
import stslex.com.datasources.user.model.UserEntity
import stslex.com.datasources.user.model.UserUpdateEntity
import stslex.com.datasources.user.table.DatabaseFactory.dbQuery
import stslex.com.datasources.user.table.UserEntitiesTable
import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import java.util.*

class UserDataSourceImpl : UserDataSource {

    override suspend fun isUserExist(
        username: String
    ): Boolean = dbQuery {
        UserEntitiesTable
            .select {
                UserEntitiesTable.username eq username
            }
            .firstOrNull() != null
    }

    override suspend fun saveUser(
        user: UserAuthRequest
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

    override suspend fun getUserByUuid(
        uuid: String
    ): UserEntity? = dbQuery {
        val currentUuid = try {
            UUID.fromString(uuid)
        } catch (error: IllegalArgumentException) {
            return@dbQuery null
        }
        UserEntitiesTable
            .select {
                UserEntitiesTable.uuid eq currentUuid
            }
            .firstOrNull()
            ?.toData()
    }

    override suspend fun updateFields(
        uuid: String,
        update: UserUpdateEntity
    ): UserEntity? = dbQuery {
        val currentUuid = try {
            UUID.fromString(uuid)
        } catch (error: IllegalArgumentException) {
            return@dbQuery null
        }
        UserEntitiesTable
            .update(
                {
                    UserEntitiesTable.uuid eq currentUuid
                }
            ) {
                it[nickname] = update.nickname
            }
        UserEntitiesTable
            .select {
                UserEntitiesTable.uuid eq currentUuid
            }
            .firstOrNull()
            ?.toData()
    }

    override suspend fun getAll(
        page: Int,
        pageSize: Int
    ): List<UserEntity> = dbQuery {
        UserEntitiesTable
            .selectAll()
            .limit(
                n = pageSize,
                offset = page * pageSize.toLong()
            )
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
