package com.metrichugo.apextest.domain

import com.metrichugo.apextest.data.PokemonRepository
import javax.inject.Inject

class GetPokemonInfoUseCase @Inject constructor(
    private val repository: PokemonRepository,
){
    suspend fun getPokemonInfo(name: String) = repository.getPokemonInfo(name)
}