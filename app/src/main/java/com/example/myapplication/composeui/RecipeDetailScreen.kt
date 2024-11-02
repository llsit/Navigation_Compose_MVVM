package com.example.myapplication.composeui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.data.Recipes
import com.example.myapplication.utils.UiState
import com.example.myapplication.viewmodels.MainViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    id: Int?,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            CustomToolbarScreen(navController = navController, title = "Detail", true)
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //add your code
            LaunchedEffect(key1 = Unit) {
                getReceipesDetails(mainViewModel, id)
            }
            val state = mainViewModel.uiStateRecipesDetail.collectAsState()
            when (state.value) {
                is UiState.Success -> {
                    ProgressLoader(isLoading = false)
                    (state.value as UiState.Success<Recipes.Recipe>).data?.let {
                        RecipeDetailView(recipe = it, sharedTransitionScope, animatedContentScope)
                    }
                }

                is UiState.Loading -> {
                    ProgressLoader(isLoading = true)
                }

                is UiState.Error -> {
                    ProgressLoader(isLoading = false)
                    //Handle Error
                }
            }
        }
    }

}

private fun getReceipesDetails(mainViewModel: MainViewModel, id: Int?) {
    // Call the function to fetch recipes
    mainViewModel.getRecipesDetail(id)
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RecipeDetailView(
    recipe: Recipes.Recipe,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.image)
                    .crossfade(true)
                    .placeholderMemoryCacheKey("image-${recipe.id}") //  same key as shared element key
                    .memoryCacheKey("image-${recipe.id}") // same key as shared element key
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${recipe.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = recipe.name ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${recipe.name}"),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ingredients:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            recipe.ingredients?.forEach { ingredient ->
                Text(
                    text = "• $ingredient",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Instructions:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            recipe.instructions?.forEachIndexed { index, instruction ->
                Text(
                    text = "${index + 1}. $instruction",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}