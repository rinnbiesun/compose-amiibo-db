package com.rinnbie.amiibodb.data

import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("amiibo") val series: List<Series>
)