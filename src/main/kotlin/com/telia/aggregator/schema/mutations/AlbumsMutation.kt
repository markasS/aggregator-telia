package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Album
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class AlbumsMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "albums"
    }
    suspend fun createAlbum(post : Album) : Album =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun updateAlbum(post : Album) : Album =
        externalApiClient.put("%s/%d".format(PATH, post.id)) {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()

    suspend fun deleteAlbum(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchAlbum(id: Int, patchAlbumInput : AlbumPatchInput) : Album =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchAlbumInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AlbumPatchInput(val userId: Int?, val title: String?)