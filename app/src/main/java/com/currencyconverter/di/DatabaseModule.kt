package com.currencyconverter.di

import android.app.Application
import androidx.room.Room
import com.currencyconverter.data.datasource.local.CurrencyDao
import com.currencyconverter.data.datasource.local.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): LocalDatabase {
        return Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            "CURRENCY-CONVERTER-DATABASE"
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideFavouriteDao(dataBase: LocalDatabase): CurrencyDao = dataBase.currencyDao()
}