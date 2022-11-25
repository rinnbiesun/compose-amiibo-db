package com.rinnbie.amiibodb.model

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series

data class HomeData(
    val amiibos: List<Amiibo> = emptyList(),
    val series: List<Series> = emptyList()
)
