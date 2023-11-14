package com.metrichugo.apextest.data.network

import com.metrichugo.apextest.data.model.NamedAPIResource
import com.metrichugo.apextest.data.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val api: PokemonApiClient) {

    suspend fun getPokemonList(offset: Int, limit: Int): List<NamedAPIResource> {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemonList(offset, limit)
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getPokemonInfo(name: String): Pokemon?{
        return withContext(Dispatchers.IO){
            val response = api.getPokemon(name)
            response.body()
        }
    }
}