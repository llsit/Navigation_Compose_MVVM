package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.data.remote.RemoteDataSource
import com.example.myapplication.data.remote.toResultFlow
import com.example.myapplication.utils.UiState
import kotlinx.coroutines.flow.Flow

class Repository(
    private val context: Context,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getRecipes(): Flow<UiState<Recipes>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipes()
        }
    }

    suspend fun getRecipesDetail(id: Int?): Flow<UiState<Recipes.Recipe>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipesDetail(id)
        }
    }

}