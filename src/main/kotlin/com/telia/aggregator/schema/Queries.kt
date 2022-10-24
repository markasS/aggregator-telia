/*
 * Copyright 2021 Expedia, Inc
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

package com.telia.aggregator.schema

import com.expediagroup.graphql.server.operations.Query
import com.telia.aggregator.schema.models.Album
import com.telia.aggregator.schema.models.Post
import com.telia.aggregator.schema.models.Todo
import com.telia.aggregator.schema.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


abstract class ExternalApiQuery(val externalApiClient : HttpClient) : Query

data class SearchByIdsParameters(val ids: List<Int>)

class PostsQuery(externalApiClient: HttpClient) : ExternalApiQuery(externalApiClient) {
    suspend fun searchPosts(params: SearchByIdsParameters) : List<Post>  =
       if (params.ids.isEmpty()) {
           externalApiClient.get("posts").body()
       }
       else queryList(params, "posts", externalApiClient).map{it.body()}
}

class AlbumsQuery(externalApiClient: HttpClient) : ExternalApiQuery(externalApiClient)  {
    suspend fun searchAlbums(params: SearchByIdsParameters) : List<Album> =
        if (params.ids.isEmpty()) {
            externalApiClient.get("albums").body()
        }
        else queryList(params, "albums", externalApiClient).map{it.body()}

}

class TodosQuery(externalApiClient: HttpClient) : ExternalApiQuery(externalApiClient)  {
    suspend fun searchTodos(params: SearchByIdsParameters): List<Todo> =
        if (params.ids.isEmpty()) {
            externalApiClient.get("todos").body()
        }
        else queryList(params, "todos", externalApiClient).map{it.body()}
}

class UsersQuery(externalApiClient: HttpClient) : ExternalApiQuery(externalApiClient) {
    suspend fun searchUsers(params: SearchByIdsParameters): List<User> =
        if (params.ids.isEmpty()) {
            externalApiClient.get("users").body()
        } else queryList(params, "users", externalApiClient).map{it.body()}
}

private suspend fun queryList(params: SearchByIdsParameters, path : String, externalApiClient: HttpClient) : List<HttpResponse> =
    params.ids.mapNotNull{
        val response = externalApiClient.get("%s/%d".format(path, it))
        return@mapNotNull if (response.bodyAsText() == "{}") null else response}



