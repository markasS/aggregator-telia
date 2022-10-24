package com.telia.aggregator

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ApplicationTest {

    @Test
    fun testPlaygroundStatusOk() = testApplication {
        client.get("/playground").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testSdlStatusOk() = testApplication {
        client.get("/sdl").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testGraphQLQuery() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response : HttpResponse = client.post("/graphql") {
          setBody("{\"operationName\":null,\"variables\":{},\"query\":\"{searchPosts(params: {ids: [1]}) {id}}\"}")
          contentType(ContentType.Application.Json)
          accept(ContentType.Application.Json)
        }
        assertEquals( "{\"data\":{\"searchPosts\":[{\"id\":1}]}}", response.bodyAsText())
    }
}