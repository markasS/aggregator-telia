package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Todo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class TodosMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "todos"
    }
    suspend fun createTodo(post : Todo) : Todo =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun updateTodo(post : Todo) : Todo =
        externalApiClient.put("%s/%d".format(PATH, post.id)) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun deleteTodo(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchTodo(id: Int, patchTodoInput : TodoPatchInput) : Todo =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchTodoInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TodoPatchInput(val userId : Int?,
                           val title : String?,
                           val completed : Boolean?)