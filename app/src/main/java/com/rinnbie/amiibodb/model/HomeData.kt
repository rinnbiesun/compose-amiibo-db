package com.rinnbie.amiibodb.model

import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Series

data class HomeData(
    val amiibo: List<Amiibo>,
    val series: List<Series>
)
