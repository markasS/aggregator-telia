/*
 * Copyright 2021 Expedia, Inc
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

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import com.telia.aggregator.schema.AlbumsQuery
import com.telia.aggregator.schema.PostsQuery
import com.telia.aggregator.schema.TodosQuery
import com.telia.aggregator.schema.UsersQuery
import com.telia.aggregator.schema.mutations.*
import graphql.GraphQL
import io.ktor.client.*

/**
 * Custom logic for how this Ktor server loads all the queries and configuration to create the [GraphQL] object
 * needed to handle incoming requests.
 */
private val config = SchemaGeneratorConfig(supportedPackages = listOf("com.telia.aggregator", "*"))
private fun queries(externalApiClient: HttpClient) = listOf(
    TopLevelObject(PostsQuery(externalApiClient)),
    TopLevelObject(AlbumsQuery(externalApiClient)),
    TopLevelObject(TodosQuery(externalApiClient)),
    TopLevelObject(UsersQuery(externalApiClient))
)
private fun mutations(externalApiClient: HttpClient) = listOf(
    TopLevelObject(PostsMutation(externalApiClient)),
    TopLevelObject(TodosMutation(externalApiClient)),
    TopLevelObject(AlbumsMutation(externalApiClient)),
    TopLevelObject(PhotosMutation(externalApiClient)),
    TopLevelObject(CommentsMutation(externalApiClient)),
    TopLevelObject(UsersMutation(externalApiClient))
)

fun graphQLSchema(externalApiClient: HttpClient) = toSchema(config, queries(externalApiClient),
    mutations(externalApiClient))

fun getGraphQLObject(externalApiClient : HttpClient): GraphQL = GraphQL.newGraphQL(graphQLSchema(externalApiClient))
    .valueUnboxer(IDValueUnboxer())
    .build()
