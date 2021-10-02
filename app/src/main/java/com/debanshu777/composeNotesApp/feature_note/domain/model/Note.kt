package com.debanshu777.composeNotesApp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.debanshu777.composeNotesApp.ui.theme.BabyBlue
import com.debanshu777.composeNotesApp.ui.theme.LightGreen
import com.debanshu777.composeNotesApp.ui.theme.RedOrange
import com.debanshu777.composeNotesApp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int?=null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedOrange)
    }
}

class InvalidNoteException(message: String):Exception(message)
