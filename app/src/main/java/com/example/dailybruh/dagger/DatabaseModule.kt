package com.example.dailybruh.dagger

import com.example.dailybruh.database.Database
import com.example.dailybruh.viewmodel.DatabaseViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.StateFlow

@Module
class DatabaseModule {

    @Provides
    fun provideDatabaseViewModel(): DatabaseViewModel {
        return DatabaseViewModel()
    }
    @Provides
    fun provideDatabase(databaseFlow: StateFlow<Database>): Database {
        return databaseFlow.value
    }
    @Provides
    fun provideDatabaseFlow(databaseViewModel: DatabaseViewModel): StateFlow<Database> {
        return databaseViewModel.database
    }
}