/*
 * Copyright 2022 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.telia.aggregator.schema.models

import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.telia.aggregator.schema.dataloaders.DataLoadersNames
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import java.util.concurrent.CompletableFuture

@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {

    fun user(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<User> =
        dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.USER_DATA_LOADER, userId)

    fun comments(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Comment>> =
         dataFetchingEnvironment.getValueFromDataLoader(DataLoadersNames.COMMENTS_DATA_LOADER, id)
}
