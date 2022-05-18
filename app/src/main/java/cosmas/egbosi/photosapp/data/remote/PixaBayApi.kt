package cosmas.egbosi.photosapp.data.remote

import cosmas.egbosi.photosapp.BuildConfig
import cosmas.egbosi.photosapp.MainActivity
import cosmas.egbosi.photosapp.data.remote.dto.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PixaBayApi {

    companion object{
        //const val BASE_URL= "https://pixabay.com/api/"
        const val CLIENT_ID = BuildConfig.PIXELBAY_ACCESS_KEY
        val MY_KEY = System.loadLibrary("keys")

    }



    @GET("?key=$CLIENT_ID")
    suspend fun searchImages(@Query("q") query: String?): ApiResponse
}