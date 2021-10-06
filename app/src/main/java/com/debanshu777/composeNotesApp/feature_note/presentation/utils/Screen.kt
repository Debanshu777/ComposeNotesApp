package com.debanshu777.composeNotesApp.feature_note.presentation.utils

sealed class Screen(val route: String){
    object NoteScreen: Screen("note_screen")
    object DetailNoteScreen: Screen("detail_note_screen")
}
