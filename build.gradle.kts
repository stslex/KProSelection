import io.ktor.plugin.features.*
import io.ktor.plugin.features.DockerImageRegistry.Companion.dockerHub

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "stslex.com"
version = "0.0.1"

application {
    mainClass.set("stslex.com.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    val exposedVersion = "0.41.1"
    val h2Version = "2.1.214"
    val postgresVersion = "42.5.1"

    /*Exposed*/
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("org.postgresql:postgresql:$postgresVersion")

    /*Swagger*/
    implementation("io.ktor:ktor-server-swagger:$ktor_version")

    /*Koin*/
    val koinVersion = "3.4.3"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    /*Ktor*/
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-freemarker:$ktor_version")

    /*Basic Ktor*/
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
    docker {
        jreVersion.set(JreVersion.JRE_17)
        localImageName.set("sample-docker-image")
        imageTag.set("0.0.1-preview")
        portMappings.set(
            listOf(
                DockerPortMapping(
                    8080,
                    8080,
                    DockerPortMappingProtocol.TCP
                )
            )
        )
        externalRegistry.set(
            dockerHub(
                appName = provider { "kproselection" },
                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
            )
        )
    }
}