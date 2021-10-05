package com.debanshu777.composeNotesApp.feature_note.presentation.details_note

import androidx.compose.ui.focus.FocusState

sealed class DetailNoteEvent{
    data class EnteredTitle(val value:String): DetailNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): DetailNoteEvent()
    data class EnteredContent(val value:String): DetailNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): DetailNoteEvent()
    data class ChangeColor(val color: Int): DetailNoteEvent()
    object SaveNote: DetailNoteEvent()
}
