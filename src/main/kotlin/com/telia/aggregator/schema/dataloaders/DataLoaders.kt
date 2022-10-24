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

package com.telia.aggregator.schema.dataloaders

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.COMMENTS_DATA_LOADER
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.PHOTOS_DATA_LOADER
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.USER_ALBUMS_DATA_LOADER
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.USER_DATA_LOADER
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.USER_POSTS_DATA_LOADER
import com.telia.aggregator.schema.dataloaders.DataLoadersNames.Companion.USER_TODOS_DATA_LOADER
import com.telia.aggregator.schema.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.dataloader.DataLoaderFactory
import org.dataloader.DataLoaderOptions
import java.util.concurrent.CompletableFuture

class DataLoadersNames {
    companion object {
        const val USER_DATA_LOADER = "USER_LOADER"
        const val COMMENTS_DATA_LOADER = "COMMENTS_LOADER"
        const val PHOTOS_DATA_LOADER = "PHOTOS_LOADER"
        const val USER_ALBUMS_DATA_LOADER = "USER_ALBUMS_LOADER"
        const val USER_TODOS_DATA_LOADER = "USER_TODOS_LOADER"
        const val USER_POSTS_DATA_LOADER = "USER_POSTS_DATA_LOADER"
    }
}

fun userDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, User>  =
   object : KotlinDataLoader<Int, User> {
        override val dataLoaderName = USER_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, User>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("users/%d".format(id)).body() }  }
            }}, DataLoaderOptions.newOptions().setCachingEnabled(true))
    }

fun commentsDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, List<Comment>> =
    object : KotlinDataLoader<Int, List<Comment>> {
        override val dataLoaderName = COMMENTS_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Comment>>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("comments?postId=%d".format(id)).body() } }
            }}, DataLoaderOptions.newOptions().setCachingEnabled(true))
}

fun photosDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, List<Photo>> =
    object : KotlinDataLoader<Int, List<Photo>> {
        override val dataLoaderName = PHOTOS_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Photo>>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("photos?albumId=%d".format(id)).body() } }
            }}, DataLoaderOptions.newOptions().setCachingEnabled(true))
}

fun userAlbumsDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, List<Album>> =
    object : KotlinDataLoader<Int, List<Album>> {
        override val dataLoaderName = USER_ALBUMS_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Album>>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("users/%d/albums".format(id)).body() } }
            }
        }, DataLoaderOptions.newOptions().setCachingEnabled(true))
    }

fun userTodosDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, List<Todo>> =
    object : KotlinDataLoader<Int, List<Todo>> {
        override val dataLoaderName = USER_TODOS_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Todo>>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("users/%d/todos".format(id)).body() } }
            }}, DataLoaderOptions.newOptions().setCachingEnabled(true))
    }

fun userPostsDataLoader(externalApiClient : HttpClient) : KotlinDataLoader<Int, List<Post>> =
    object : KotlinDataLoader<Int, List<Post>> {
        override val dataLoaderName = USER_POSTS_DATA_LOADER
        override fun getDataLoader() = DataLoaderFactory.newDataLoader<Int, List<Post>>({ ids ->
            CompletableFuture.supplyAsync {
                runBlocking { ids.map { id -> externalApiClient.get("users/%d/posts".format(id)).body() } }
            }}, DataLoaderOptions.newOptions().setCachingEnabled(true))
    }



