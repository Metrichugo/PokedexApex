package com.metrichugo.apextest.domain

import com.metrichugo.apextest.data.PokemonRepository
import javax.inject.Inject

class getPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository,
) {
    suspend fun getPokemonList(offset: Int, limit: Int) = repository.getPokemonList(offset, limit)
}