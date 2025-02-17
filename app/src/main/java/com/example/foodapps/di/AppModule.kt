package com.example.foodapps.di

import android.app.Application
import com.example.foodapps.data.manger.LocalUserMangerImpl
import com.example.foodapps.data.remote.FoodApiService
import com.example.foodapps.data.remote.repository.LoginRepositoryImpl
import com.example.foodapps.domain.manger.LocalUserManager
import com.example.foodapps.domain.repository.LoginRepository
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import com.example.foodapps.domain.usecases.app_entry.ReadAppEntry
import com.example.foodapps.domain.usecases.app_entry.SaveAppEntry
import com.example.foodapps.domain.usecases.login_case.LoginInvokeCase
import com.example.foodapps.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserMangerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

@Provides
@Singleton
fun  provideOkHttpClient() : OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
}

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
//
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : FoodApiService{
        return retrofit.create(FoodApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(foodApi: FoodApiService): LoginRepository{
        return LoginRepositoryImpl(foodApi)
    }
    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginInvokeCase{
        return LoginInvokeCase(loginRepository)
    }
}