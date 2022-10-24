package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Photo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class PhotosMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "photos"
    }
    suspend fun createPhoto(photo : Photo) : Photo =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(photo)
        }.body()

    suspend fun updatePhoto(photo : Photo) : Photo =
        externalApiClient.put("%s/%d".format(PATH, photo.id)) {
            contentType(ContentType.Application.Json)
            setBody(photo)
        }.body()

    suspend fun deletePhoto(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchPhoto(id: Int, patchPhotoInput : PhotoPatchInput) : Photo =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchPhotoInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PhotoPatchInput(val albumId : Int?,
                           val title : String?,
                           val url : String?,
                           val thumbnailUrl : String?)