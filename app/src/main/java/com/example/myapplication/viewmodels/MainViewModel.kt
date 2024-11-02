package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Recipes
import com.example.myapplication.data.Repository
import com.example.myapplication.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiStateRecipesList = MutableStateFlow<UiState<Recipes>>(UiState.Loading)
    val uiStateRecipesList: StateFlow<UiState<Recipes>> = _uiStateRecipesList

    private val _uiStateRecipesDetail = MutableStateFlow<UiState<Recipes.Recipe>>(UiState.Loading)
    val uiStateRecipesDetail: StateFlow<UiState<Recipes.Recipe>> = _uiStateRecipesDetail

    fun getRecipesList() = viewModelScope.launch {
        repository.getRecipes().collect {
            when (it) {
                is UiState.Success -> {
                    _uiStateRecipesList.value = UiState.Success(it.data)
                }

                is UiState.Loading -> {
                    _uiStateRecipesList.value = UiState.Loading
                }

                is UiState.Error -> {
                    //Handle Error
                    _uiStateRecipesList.value = UiState.Error(it.message)
                }
            }
        }
    }

    fun getRecipesDetail(id: Int?) = viewModelScope.launch {
        repository.getRecipesDetail(id).collect {
            when (it) {
                is UiState.Success -> {
                    _uiStateRecipesDetail.value = UiState.Success(it.data)
                }

                is UiState.Loading -> {
                    _uiStateRecipesDetail.value = UiState.Loading
                }

                is UiState.Error -> {
                    //Handle Error
                    _uiStateRecipesDetail.value = UiState.Error(it.message)
                }
            }
        }
    }
}