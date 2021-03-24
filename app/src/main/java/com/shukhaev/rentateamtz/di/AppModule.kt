package com.shukhaev.rentateamtz.di

import android.content.Context
import androidx.room.Room
import com.shukhaev.rentateamtz.db.UsersDatabase
import com.shukhaev.rentateamtz.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://reqres.in/api/"
private const val DATABASE_NAME = "UsersDataBase"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): NetworkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)

    @Singleton
    @Provides
    fun provideUserDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UsersDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideRunDao(db: UsersDatabase) = db.getUserDao()
}