package com.metrichugo.apextest.ui.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metrichugo.apextest.R
import com.metrichugo.apextest.data.model.NamedAPIResource
import com.metrichugo.apextest.databinding.PokemonItemBinding
import com.squareup.picasso.Picasso
import java.util.*

class PokedexAdapter(
    val clickListener: ClickListener,
) : RecyclerView.Adapter<PokedexAdapter.PokemonListViewHolder>() {

    val pokemonList = mutableListOf<NamedAPIResource>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonListViewHolder(layoutInflater.inflate(R.layout.pokemon_item, parent, false))
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon, clickListener)
    }

    fun updateData(pokemonList: List<NamedAPIResource>){
        val size = this.pokemonList.size
        this.pokemonList.addAll(pokemonList.takeLast(LIMIT))
        notifyItemRangeInserted(size, LIMIT)
    }

    class PokemonListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = PokemonItemBinding.bind(view)

        fun bind(pokemon: NamedAPIResource, clickListener: ClickListener) {
            val pokemonId = Uri.parse(pokemon.url).lastPathSegment?.toInt()
            binding.idName.text = pokemon.name
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png").into(binding.pokemonImage)
            binding.root.setOnClickListener { clickListener.onClick(pokemon) }
        }
    }

    class ClickListener(val clickListener: (name: String) -> Unit) {
        fun onClick(pokemon: NamedAPIResource) = clickListener(pokemon.name)
    }

    companion object{
        const val LIMIT = 20
    }
}