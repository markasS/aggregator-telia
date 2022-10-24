package com.telia.aggregator.schema.models

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val albumId : Int,
    val id: Int,
    val title : String,
    val url : String,
    val thumbnailUrl : String)