package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Post
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class PostsMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "posts"
    }
    suspend fun createPost(post : Post) : Post =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun updatePost(post : Post) : Post =
        externalApiClient.put("%s/%d".format(PATH, post.id)) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun deletePost(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchPost(id: Int, patchPostInput : PostPatchInput) : Post =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchPostInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostPatchInput(val userId: Int?, val title: String?, val body: String?)
