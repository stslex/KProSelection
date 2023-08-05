package stslex.com

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.junit.Test
import kotlin.test.assertTrue

class TestAppConfig {

    private val config = HoconApplicationConfig(ConfigFactory.load())

    @Test
    fun ktorDeploymentPort() {
        val property = getPropertyString("ktor.deployment.port")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun ktorDeploymentHost() {
        val property = getPropertyString("ktor.deployment.host")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun ktorApplicationModules() {
        val property = getPropertyList("ktor.application.modules")
        assertTrue(property.isNotEmpty())
    }

    @Test
    fun postgresUrl() {
        val property = getPropertyString("postgres.url")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun postgresUser() {
        val property = getPropertyString("postgres.user")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun postgresPassword() {
        val property = getPropertyString("postgres.password")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun apiKey() {
        val property = getPropertyString("apiKey")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun jwtAuthSecret() {
        val property = getPropertyString("jwt.auth.secret")
        assertTrue(property.isNotBlank())
    }

    @Test
    fun jwtUnAuthSecret() {
        val property = getPropertyString("jwt.unAuth.secret")
        assertTrue(property.isNotBlank())
    }

    private fun getPropertyString(path: String): String = try {
        config.property(path).getString()
    } catch (exception: ApplicationConfigurationException) {
        ""
    }

    private fun getPropertyList(path: String): List<String> = try {
        config.property(path).getList()
    } catch (exception: ApplicationConfigurationException) {
        emptyList()
    }
}
