package com.telia.aggregator.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.telia.aggregator.schema.dataloaders.DataLoadersNames
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import java.util.concurrent.CompletableFuture

@Serializable
data class User(
    val id: Int,
    val email: String,
    val username: String,
    val phone: String,
    val name: String,
    val website: String,
    val company: Company,
    val address: Address
) {
    fun albums(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Album>> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_ALBUMS_DATA_LOADER, id)

    fun todos(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Todo>> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_TODOS_DATA_LOADER, id)

    fun posts(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Post>> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_POSTS_DATA_LOADER, id)
}

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo : LatLng
)

data class LatLng(
    val lat: Double,
    val lng: Double
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)