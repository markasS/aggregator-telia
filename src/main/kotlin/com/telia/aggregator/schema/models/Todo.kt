package com.telia.aggregator.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.telia.aggregator.schema.dataloaders.DataLoadersNames
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import java.util.concurrent.CompletableFuture

@Serializable
data class Todo(
    val id: Int,
    val userId : Int,
    val title : String,
    val completed : Boolean) {

    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_DATA_LOADER, userId)
}