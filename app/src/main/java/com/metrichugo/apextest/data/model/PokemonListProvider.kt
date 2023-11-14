package com.metrichugo.apextest.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonListProvider @Inject constructor() {
    var pokemons: MutableList<NamedAPIResource> = mutableListOf()
    var currentPage : MutableList<NamedAPIResource> = mutableListOf()
}