package com.example.saller.di

import com.example.saller.data.local.repository.AppRepositoryImpl
import com.example.saller.domain.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun bindRepo(impl: AppRepositoryImpl): AppRepository
}