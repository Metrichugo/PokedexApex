package com.metrichugo.apextest.data

import com.metrichugo.apextest.data.model.*
import com.metrichugo.apextest.data.network.PokemonService
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokemonService,
    private val pokemonListProvider: PokemonListProvider,
    private val pokemonProvider: PokemonProvider
) {
    suspend fun getPokemonList(offset: Int, limit: Int): List<NamedAPIResource> {
        val response = api.getPokemonList(offset, limit)
        pokemonListProvider.pokemons.addAll(response)
        return pokemonListProvider.pokemons
    }

    suspend fun getPokemonInfo(name: String): Pokemon {
        val response = api.getPokemonInfo(name)
        pokemonProvider.pokemon = response
        return pokemonProvider.pokemon ?: Pokemon(
            id = 0,
            name = "MissingNo",
            height = 0,
            order = 0,
            weight = 0,
            sprites = PokemonSprites(null, null, null, null)
        )
    }
}