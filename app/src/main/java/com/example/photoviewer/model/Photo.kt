package com.example.photoviewer.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    @SerializedName("download_url") val downloadUrl: String
)
