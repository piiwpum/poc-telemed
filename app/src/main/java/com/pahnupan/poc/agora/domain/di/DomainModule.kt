package com.pahnupan.poc.agora.domain.di

import com.pahnupan.poc.agora.data.repository.TelemedRepositoryImpl
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun provideCreateRoomRepository(repositoryImpl: TelemedRepositoryImpl): TelemedRepository

}

