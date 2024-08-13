package com.example.androidrepositoriesapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidrepositoriesapplication.data.api.ApiService
import com.example.androidrepositoriesapplication.data.db.AndroidRepositoryDao
import com.example.androidrepositoriesapplication.data.db.AppDatabase
import com.example.androidrepositoriesapplication.data.models.database_models.HeaderLinkEntity
import com.example.androidrepositoriesapplication.data.repository.AndroidDataRepositoryImpl
import com.example.androidrepositoriesapplication.domain.repository.AndroidDataRepository
import com.example.androidrepositoriesapplication.ui.theme.API_URL
import com.example.androidrepositoriesapplication.ui.theme.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// This is the Hilt Dagger Module / DI module in Which we define the dependencies which we will provide
// to other objects
@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    // Database Provider The AndroidDataRepository depend on it
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    provideAppDatabase(appContext).headerLinkDao.insertRecord(HeaderLinkEntity(id = 1, prevPage = -1, nextPage = -1, firstPage = -1, lastPage = -1))
                }
            }
        }).build()
    }

    // HTTP Client Provider Api Service depend on it
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Set read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // Set write timeout
            .build()

    }

    // Api Service Provider the AndroidDataRepository depend on it
    @Provides
    @Singleton
    fun provideRestApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // The AndroidDataRepository provider the DetailsViewModel and the RepositoriesViewModel depend on it
    @Provides
    @Singleton
    fun provideAndroidDataRepository(
        appDatabase: AppDatabase,
        apiService: ApiService,
    ): AndroidDataRepository {
        return AndroidDataRepositoryImpl(
            appDatabase = appDatabase,
            apiService = apiService
        )
    }
}

