package com.example.myapplication.data.remote

import com.example.myapplication.data.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("recipes")
    suspend fun getRecipes(): Response<Recipes>

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(@Path("id") id: Int?): Response<Recipes.Recipe>
}