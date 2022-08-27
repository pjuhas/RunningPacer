package sk.upjs.vma.runningpacer.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sk.upjs.vma.runningpacer.common.presentation.dataStoreRace
import sk.upjs.vma.runningpacer.common.presentation.dataStoreTraining
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.data.data_source.TrainingPaceDatabase
import sk.upjs.vma.runningpacer.feature_vdot.data.repository.TrainingPaceImpl
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository
import sk.upjs.vma.runningpacer.feature_vdot.data.repository.DefaultDataStoreRepository
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrainingPaceDatabase(app: Application): TrainingPaceDatabase {
        return Room.databaseBuilder(
            app,
            TrainingPaceDatabase::class.java,
            TrainingPaceDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrainingPaceRepository(db: TrainingPaceDatabase): TrainingPaceRepository {
        return TrainingPaceImpl(db.trainingPaceDao)
    }

    @Provides
    @Singleton
    fun provideTrainingPaceUseCases(
        repository: TrainingPaceRepository,
        repositoryProto: DefaultDataStoreRepository
    ): TrainingPaceUseCases {
        return TrainingPaceUseCases(
            getTrainingPacesOrder = GetTrainingPacesOrder(repository),
            addTrainingPace = AddTrainingPace(repository),
            calculatePace = CalculatePace(),
            regularTimeToSec = RegularTimeToSec(),
            getRacePaces = GetRacePaces(repositoryProto),
            getTrainingPaces = GetTrainingPaces(repositoryProto),
            loadRaceData = LoadRaceData(),
            loadTrainingData = LoadTrainingData()
        )
    }


    @Provides
    @Reusable
    fun provideProtoDataStoreRace(@ApplicationContext context: Context) = context.dataStoreRace

    @Provides
    @Reusable
    fun provideProtoDataStoreTraining(@ApplicationContext context: Context) = context.dataStoreTraining


    @Provides
    @Singleton
    fun providesDataStoreRepository(
        raceDataStore: DataStore<RaceTableData>,
        trainingDataStore: DataStore<TrainingTableData>
    ): DataRepository {
        return DefaultDataStoreRepository(
            raceDataStore,
            trainingDataStore
        )
    }
}

