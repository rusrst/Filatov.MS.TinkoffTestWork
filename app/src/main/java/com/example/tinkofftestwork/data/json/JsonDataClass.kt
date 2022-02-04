package com.example.tinkofftestwork.data.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonDataClass (val result: List<JsonItemDataClass>, val totalCount: Int?)

@Serializable
data class JsonItemDataClass(val id: Int? = null,
                             val description: String? = null,
                             val votes: Int? = null,
                             val author: String? = null,
                             val date: String? = null,
                             val gifURL: String? = null,
                             val gifSize: Int? = null,
                             val previewURL: String? = null,
                             val videoURL: String? = null,
                             val videoPath: String? = null,
                             val videoSize: Int? = null,
                             val type: String? = null,
                             val width: String? = null,
                             val height: String? = null,
                             val commentsCount: Int? = null,
                             val fileSize: Int? = null,
                             val canVote: Boolean? = null)