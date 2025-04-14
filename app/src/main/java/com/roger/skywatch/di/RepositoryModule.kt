package com.roger.skywatch.di

import com.roger.skywatch.data.repository.IYoutubeRepository
import com.roger.skywatch.data.repository.YoutubeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindYoutubeRepository(youtubeRepository: YoutubeRepository): IYoutubeRepository
}