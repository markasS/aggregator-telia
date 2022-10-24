package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Comment
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class CommentsMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "comments"
    }
    suspend fun createComment(comment : Comment) : Comment =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(comment)
        }.body()

    suspend fun updateComment(comment : Comment) : Comment =
        externalApiClient.put("%s/%d".format(PATH, comment.id)) {
            contentType(ContentType.Application.Json)
            setBody(comment)
        }.body()

    suspend fun deleteComment(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchComment(id: Int, patchCommentInput : CommentPatchInput) : Comment =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchCommentInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CommentPatchInput(val postId : Int?,
                             val name : String?,
                             val email : String?,
                             val body : String?)