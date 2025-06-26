package com.example.foodapps.di

import android.app.Application
import com.example.foodapps.data.manger.LocalUserMangerImpl
import com.example.foodapps.data.remote.FoodApiService
import com.example.foodapps.data.remote.repository.AuthRepositoryImpl
import com.example.foodapps.data.remote.repository.LikedItemsRepositoryImpl
import com.example.foodapps.data.remote.repository.MenuCategoryRepositoryImpl
import com.example.foodapps.data.remote.repository.NotificationRepositoryImpl
import com.example.foodapps.data.remote.repository.RestaurantDetailsRepositoryImpl
import com.example.foodapps.data.remote.repository.RestaurantRepositoryImpl
import com.example.foodapps.data.remote.repository.UserDataRepositoryImpl
import com.example.foodapps.domain.manger.LocalUserManager
import com.example.foodapps.domain.repository.AuthRepository
import com.example.foodapps.domain.repository.LikedItemsRepository
import com.example.foodapps.domain.repository.LoginRepository
import com.example.foodapps.domain.repository.MenuCategoryRepository
import com.example.foodapps.domain.repository.NotificationRepository
import com.example.foodapps.domain.repository.RestaurantDetailsRepository
import com.example.foodapps.domain.repository.RestaurantRepository
import com.example.foodapps.domain.repository.UserDataRepository
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import com.example.foodapps.domain.usecases.app_entry.ReadAppEntry
import com.example.foodapps.domain.usecases.app_entry.SaveAppEntry
import com.example.foodapps.domain.usecases.login_case.LoginUseCase
import com.example.foodapps.utils.Constants.BASE_URL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserDataRepository(firestore: FirebaseFirestore): UserDataRepository =
        UserDataRepositoryImpl(firestore)

    @Provides
    @Singleton // Repository can be singleton if it doesn't hold changing state
    fun provideMenuCategoryRepository(firestore: FirebaseFirestore): MenuCategoryRepository {
        return MenuCategoryRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideRestaurantRepository(firestore: FirebaseFirestore): RestaurantRepository {
        return RestaurantRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideRestaurantDetailsRepository(firestore: FirebaseFirestore , firebaseAuth: FirebaseAuth): RestaurantDetailsRepository {
        return RestaurantDetailsRepositoryImpl(firestore , firebaseAuth)
    }

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

    // --- ADD THIS ---
    @Provides
    @Singleton
    fun provideLikedItemsRepository(firestore: FirebaseFirestore): LikedItemsRepository {
        return LikedItemsRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepositoryImpl()
    }

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

}