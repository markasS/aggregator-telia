package com.telia.aggregator.schema.mutations

import com.expediagroup.graphql.server.operations.Mutation
import io.ktor.client.*

abstract class ExternalApiMutation (val externalApiClient : HttpClient) : Mutation