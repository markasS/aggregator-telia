package com.telia.aggregator.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.telia.aggregator.schema.dataloaders.DataLoadersNames
import graphql.schema.DataFetchingEnvironment
import java.util.concurrent.CompletableFuture

data class Album(val id: Int, val userId: Int, val title: String) {

    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_DATA_LOADER, userId)

    fun photos(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Photo>> =
         dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.PHOTOS_DATA_LOADER, id)
}