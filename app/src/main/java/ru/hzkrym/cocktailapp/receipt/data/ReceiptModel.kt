package ru.hzkrym.cocktailapp.receipt.data

data class ReceiptModel(
    val title: String,
    val receipt: String,
    val imageUrl: String,
    val ingredients: List<IngredientModel> = listOf()
)