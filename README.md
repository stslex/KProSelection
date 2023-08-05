# KProSelection

[![Deployed Test CI](https://github.com/stslex/KProSelection/actions/workflows/test-deploy.yml/badge.svg)](https://github.com/stslex/KProSelection/actions/workflows/test-deploy.yml)

[![Deployed Prod CI](https://github.com/stslex/KProSelection/actions/workflows/prod-deploy.yml/badge.svg)](https://github.com/stslex/KProSelection/actions/workflows/prod-deploy.yml)

## Description

Backend Application written on Kotlin using Ktor.

## Features

- [Authorization/Registration](https://ktor.io/docs/authentication.html)
    - [JWT](https://ktor.io/docs/jwt.html)
    - [Bearer](https://ktor.io/docs/bearer.html)
- Database
    - [Exposed](https://github.com/JetBrains/Exposed)
    - [Postgres](https://www.postgresql.org/docs/)

## Future features

- WebSockets
- H2 (for caching)
- etc... there'll be more info

## [Deployment](https://ktor.io/docs/deploy.html)

For Deployment use Docker - [official documentation](https://ktor.io/docs/docker.html) with Github actions
