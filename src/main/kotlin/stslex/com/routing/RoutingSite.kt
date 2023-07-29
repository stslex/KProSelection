package stslex.com.routing

import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

private const val SITE_FOLDER = "site"
private const val SITE_INDEX = "index.html"
private const val SITE_REMOTE_PATH = ""

fun Routing.routingSite() {
    staticFiles(
        remotePath = SITE_REMOTE_PATH,
        dir = File(SITE_FOLDER),
        index = SITE_INDEX
    )
}