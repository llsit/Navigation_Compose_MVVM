package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.composeui.RecipeDetailScreen
import com.example.myapplication.composeui.RecipesScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.Routes
import com.example.myapplication.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedTransitionLayout {
                val mainViewModel: MainViewModel = koinViewModel()
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LIST_SCREEN) {
                    composable(Routes.LIST_SCREEN) {
                        RecipesScreen(
                            navigation = navController, mainViewModel,
                            this@SharedTransitionLayout,
                            this@composable
                        )
                    }
                    composable(Routes.DETAIL_SCREEN, arguments = listOf(navArgument("idValue") {
                        type = NavType.IntType
                    })) { navBackStackEntry ->
                        RecipeDetailScreen(
                            navController,
                            mainViewModel,
                            navBackStackEntry.arguments?.getInt(Routes.Values.IDVALUE, 0),
                            this@SharedTransitionLayout,
                            this@composable
                        )
                    }
                }
            }
        }

    }
}
