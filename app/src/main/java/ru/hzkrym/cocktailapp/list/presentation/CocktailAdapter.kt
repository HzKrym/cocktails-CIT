package ru.hzkrym.cocktailapp.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.hzkrym.cocktailapp.databinding.CocktailItemBinding

class CocktailAdapter: RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    private val list = mutableListOf<CocktailViewModel>()

    class CocktailViewHolder(
        private val binding: CocktailItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun initViewModel(viewModel: CocktailViewModel) {
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = CocktailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.initViewModel(list[position])
    }

    fun updateList(newList: List<CocktailViewModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}