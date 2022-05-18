package cosmas.egbosi.photosapp.di

import android.app.Application
import androidx.room.Room
import cosmas.egbosi.photosapp.data.local.ImageDatabase
import cosmas.egbosi.photosapp.data.remote.PixaBayApi
import cosmas.egbosi.photosapp.data.repository.AppRepository
import cosmas.egbosi.photosapp.data.util.Constants.BASE_URL
import cosmas.egbosi.photosapp.domain.repository.ImageRepository
import cosmas.egbosi.photosapp.domain.use_case.GetImages
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetImagesUseCase(repository: ImageRepository): GetImages {
        return GetImages(
            repository = repository
        )
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideImagesRepository(
        db: ImageDatabase,
        pixabayApi: PixaBayApi

    ): ImageRepository {
        return AppRepository(
            pixabayApi = pixabayApi,
            imageDao = db.dao
        )
    }

    @Provides
    @Singleton
    fun provideImageDatabase(application: Application): ImageDatabase {
        return Room.databaseBuilder(
            application,
            ImageDatabase::class.java,
            "images_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePixabayApi(okHttpClient: OkHttpClient): PixaBayApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(PixaBayApi::class.java)
    }
}