package com.metrichugo.apextest.data.network

import com.metrichugo.apextest.data.model.NamedAPIResourceList
import com.metrichugo.apextest.data.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiClient {
    @GET("pokemon/?")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<NamedAPIResourceList>

    @GET("pokemon/{name}/")
    suspend fun getPokemon(
        @Path("name") name: String
    ): Response<Pokemon>
}