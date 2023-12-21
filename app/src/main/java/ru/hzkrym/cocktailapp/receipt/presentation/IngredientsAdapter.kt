package ru.hzkrym.cocktailapp.receipt.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.hzkrym.cocktailapp.databinding.IngredientItemBinding
import ru.hzkrym.cocktailapp.receipt.data.IngredientModel

class IngredientsAdapter(
    val items: List<IngredientModel>
): RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(
        private val binding: IngredientItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientModel) {
            with(binding) {
                ingredientTitle.text = item.name
                ingredientMeasure.text = item.measure
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding = IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientsViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(items[position])
    }
}