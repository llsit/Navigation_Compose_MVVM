package com.example.myapplication.data.remote

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getRecipes() = apiService.getRecipes()
    suspend fun getRecipesDetail(id: Int?) = apiService.getRecipeDetails(id)
}