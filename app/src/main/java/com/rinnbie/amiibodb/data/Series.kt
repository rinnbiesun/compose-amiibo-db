package com.rinnbie.amiibodb.data

data class Series(
    val key: String,
    val name: String,
    @Transient var defaultAmiibo: Amiibo? = null
)
