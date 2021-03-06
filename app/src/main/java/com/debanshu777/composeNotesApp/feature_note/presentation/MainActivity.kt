package com.debanshu777.composeNotesApp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.debanshu777.composeNotesApp.feature_note.presentation.details_note.DetailScreen
import com.debanshu777.composeNotesApp.feature_note.presentation.notes.NotesScreen
import com.debanshu777.composeNotesApp.feature_note.presentation.utils.Screen
import com.debanshu777.composeNotesApp.ui.theme.ComposeNotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNotesAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ){
                        composable(route = Screen.NoteScreen.route){
                            NotesScreen(navController = navController)
                        }
                        composable(route = Screen.DetailNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name="noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name="noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )

                        ){
                            val color =it.arguments?.getInt("noteColor")?:-1
                            DetailScreen(navController = navController, noteColor = color)
                        }
                    }
                }
            }
        }
    }
}
