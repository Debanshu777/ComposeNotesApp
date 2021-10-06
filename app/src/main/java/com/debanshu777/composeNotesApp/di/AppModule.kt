package com.debanshu777.composeNotesApp.di

import android.app.Application
import androidx.room.Room
import com.debanshu777.composeNotesApp.feature_note.data.data_source.NoteDatabase
import com.debanshu777.composeNotesApp.feature_note.data.repository.NoteRepositoryImpl
import com.debanshu777.composeNotesApp.feature_note.domain.repository.NoteRepository
import com.debanshu777.composeNotesApp.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application):NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase):NoteRepository{
        return  NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository):NoteUseCases{
        return  NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}
