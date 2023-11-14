package com.metrichugo.apextest.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.metrichugo.apextest.R
import com.metrichugo.apextest.databinding.PokemonInfoBinding
import com.metrichugo.apextest.ui.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class PokemonInfoFragment : Fragment() {
    private lateinit var binding: PokemonInfoBinding
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private lateinit var name: String
    private val args: PokemonInfoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PokemonInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = args.pokemonName

        pokemonViewModel.onCreate(name)

        pokemonViewModel._pokemon.observe(viewLifecycleOwner, Observer { pokemon ->
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