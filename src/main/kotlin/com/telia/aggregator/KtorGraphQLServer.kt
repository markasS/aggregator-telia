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

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.telia.aggregator.schema.dataloaders.*
import io.ktor.client.*
import io.ktor.server.request.*

/**
 * Helper method for how this Ktor example creates the common [GraphQLServer] object that
 * can handle requests.
 */
class KtorGraphQLServer(
    requestParser: KtorGraphQLRequestParser,
    contextFactory: KtorGraphQLContextFactory,
    requestHandler: GraphQLRequestHandler
) : GraphQLServer<ApplicationRequest>(requestParser, contextFactory, requestHandler)

fun getGraphQLServer(mapper: ObjectMapper, externalApiClient : HttpClient): KtorGraphQLServer {
    val dataLoaderRegistryFactory = KotlinDataLoaderRegistryFactory(
        userDataLoader(externalApiClient), commentsDataLoader(externalApiClient),
        photosDataLoader(externalApiClient), userTodosDataLoader(externalApiClient),
        userAlbumsDataLoader(externalApiClient), userPostsDataLoader(externalApiClient)
    )
    val requestParser = KtorGraphQLRequestParser(mapper)
    val contextFactory = KtorGraphQLContextFactory()
    val graphQL = getGraphQLObject(externalApiClient)
    val requestHandler = GraphQLRequestHandler(graphQL, dataLoaderRegistryFactory)

    return KtorGraphQLServer(requestParser, contextFactory, requestHandler)
}
