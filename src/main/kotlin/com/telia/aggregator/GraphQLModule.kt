/*
 * Copyright 2022 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.telia.aggregator

import com.expediagroup.graphql.generator.extensions.print
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.graphQLModule() {
    install(Routing)

    val externalApiClient = externalApiClient(environment.config.propertyOrNull("client.host.url")!!.getString())

    routing {
        post("graphql") {
            KtorServer(externalApiClient).handle(this.call)
        }

        get("sdl") {
            call.respondText(graphQLSchema(externalApiClient).print())
        }

        get("playground") {
            this.call.respondText(buildPlaygroundHtml("graphql", "subscriptions"), ContentType.Text.Html)
        }
    }
}

private fun buildPlaygroundHtml(graphQLEndpoint: String, subscriptionsEndpoint: String) =
    Application::class.java.classLoader.getResource("graphql-playground.html")?.readText()
        ?.replace("\${graphQLEndpoint}", graphQLEndpoint)
        ?.replace("\${subscriptionsEndpoint}", subscriptionsEndpoint)
        ?: throw IllegalStateException("graphql-playground.html cannot be found in the classpath")
