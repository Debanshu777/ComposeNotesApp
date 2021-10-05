package com.debanshu777.composeNotesApp.feature_note.presentation.details_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debanshu777.composeNotesApp.feature_note.domain.model.InvalidNoteException
import com.debanshu777.composeNotesApp.feature_note.domain.model.Note
import com.debanshu777.composeNotesApp.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _noteTitle = mutableStateOf(NotesTextFieldState(
        hint = "Enter title ..."
    ))
    val noteTitle: State<NotesTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NotesTextFieldState(
        hint = "Enter Content ..."
    ))
    val noteContent: State<NotesTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId :Int?=null

    init{
        savedStateHandle.get<Int>("noteId")?.let{ noteId->
            if(noteId !=-1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also{ note->
                        currentNoteId=note.id
                        _noteTitle.value=noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value=_noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value=note.color
                    }
                }
            }
        }
    }

    fun onEvent( event:DetailNoteEvent){
        when(event){
            is DetailNoteEvent.EnteredTitle ->{
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is DetailNoteEvent.ChangeTitleFocus ->{
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is DetailNoteEvent.EnteredContent ->{
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is DetailNoteEvent.ChangeContentFocus ->{
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is DetailNoteEvent.ChangeColor ->{
                _noteColor.value = event.color
            }
            is DetailNoteEvent.SaveNote ->{
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class  UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}