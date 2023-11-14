package com.metrichugo.apextest.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonProvider @Inject constructor() {
    var pokemon: Pokemon? = null
}