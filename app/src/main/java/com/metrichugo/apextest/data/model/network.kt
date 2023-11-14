package com.metrichugo.apextest.data.model

data class NamedAPIResourceList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NamedAPIResource>
)

data class NamedAPIResource(
    val name: String,
    val url: String
)