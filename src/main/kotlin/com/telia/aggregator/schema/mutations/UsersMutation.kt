package com.telia.aggregator.schema.mutations

import com.fasterxml.jackson.annotation.JsonInclude
import com.telia.aggregator.schema.models.Address
import com.telia.aggregator.schema.models.Company
import com.telia.aggregator.schema.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

class UsersMutation(externalApiClient: HttpClient) : ExternalApiMutation(externalApiClient) {
    private companion object {
        const val PATH = "users"
    }
    suspend fun createUser(user : User) : User =
        externalApiClient.post(PATH) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()

    suspend fun updateUser(user : User) : User =
        externalApiClient.put("%s/%d".format(PATH, user.id)) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()

    suspend fun deleteUser(id : Int) : Int  {
        externalApiClient.delete("%s/%d".format(PATH, id))
        return id
    }

    suspend fun patchUser(id: Int, patchUserInput : UserPatchInput) : User =
        externalApiClient.patch("%s/%d".format(PATH, id)) {
            contentType(ContentType.Application.Json)
            setBody(patchUserInput)
        }.body()
}

@Serializable
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserPatchInput(val email: String?,
                          val username: String?,
                          val phone: String?,
                          val name: String?,
                          val website: String?,
                          val company: Company?,
                          val address: Address?
)