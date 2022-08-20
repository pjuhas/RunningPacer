package sk.upjs.vma.runningpacer.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.upjs.vma.runningpacer.feature_vdot.data.data_source.TrainingPaceDatabase
import sk.upjs.vma.runningpacer.feature_vdot.data.repository.TrainingPaceImpl
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.AddTrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.CalcutatePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.GetTrainingPaces
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrainingPaceDatabase(app: Application) : TrainingPaceDatabase {
        return Room.databaseBuilder(app,
            TrainingPaceDatabase::class.java,
            TrainingPaceDatabase.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideTrainingPaceRepository(db: TrainingPaceDatabase) : TrainingPaceRepository {
        return TrainingPaceImpl(db.trainingPaceDao)
    }

    @Provides
    @Singleton
    fun provideTrainingPaceUseCases(repository: TrainingPaceRepository) : TrainingPaceUseCases {
        return TrainingPaceUseCases(
            getTrainingPaces = GetTrainingPaces(repository),
            addTrainingPace = AddTrainingPace(repository),
            calculatePace = CalcutatePace()
        )
    }
}