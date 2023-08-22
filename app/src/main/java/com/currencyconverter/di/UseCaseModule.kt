package com.currencyconverter.di

import com.currencyconverter.domain.repository.HomeRepository
import com.currencyconverter.domain.usecase.GetLatestCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideLatestCurrencyUseCase(repository: HomeRepository) =
        GetLatestCurrencyUseCase(repository)
}