package com.example.notesapplication.di

import android.app.Application
import androidx.room.Room
import com.example.notesapplication.model.NotesDatabase
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
    fun provideDatabase(app: Application): NotesDatabase {
        val db= Room.databaseBuilder(app,NotesDatabase::class.java,"notes_database").build()
        return db
    }
}