package com.metrichugo.apextest.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.metrichugo.apextest.R
import com.metrichugo.apextest.databinding.PokemonInfoBinding
import com.metrichugo.apextest.ui.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class PokemonInfo : AppCompatActivity() {
    private lateinit var binding: PokemonInfoBinding
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let {
            name = it.getString("pokemon_name", "")
        }

        binding = PokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonViewModel.onCreate(name)

        pokemonViewModel._pokemon.observe(this, Observer { pokemon ->
            binding.name.text = getString(R.string.pokemon_name, pokemon.name)
            binding.order.text = getString(R.string.pokemon_order, pokemon.order)
            binding.pokemonHeight.text = getString(R.string.pokemon_height, pokemon.height)
            binding.pokemonWeight.text = getString(R.string.pokemon_weight, pokemon.weight)
            when (Random.nextInt(1, 5)) {
                1 -> pokemon.sprites.frontDefault
                2 -> pokemon.sprites.frontShiny
                3 -> pokemon.sprites.backDefault
                else -> pokemon.sprites.backShiny
            }?.also { sprite ->
                Picasso.get().load(sprite).into(binding.sprite)
            } ?: Picasso.get().load(R.drawable.missing_no).into(binding.sprite)

        })
    }
}