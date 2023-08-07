package com.example.products_app.di

import com.example.products_app.data.DataRepository
import com.example.products_app.data.DataRepositorySource
import com.example.products_app.data.remoteData.RemoteData
import com.example.products_app.data.remoteData.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataRepositorySource

    @Binds
    @Singleton
    abstract fun provideRemoteData(remoteData: RemoteData): RemoteDataSource

}