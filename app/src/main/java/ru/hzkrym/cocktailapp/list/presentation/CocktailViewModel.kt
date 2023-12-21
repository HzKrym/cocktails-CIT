package ru.hzkrym.cocktailapp.list.presentation

data class CocktailViewModel(
    val title: String,
    val onClickAction: () -> Unit
)