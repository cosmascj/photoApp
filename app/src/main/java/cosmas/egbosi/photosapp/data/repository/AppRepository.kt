package cosmas.egbosi.photosapp.data.repository

import cosmas.egbosi.photosapp.data.local.ImageDao
import cosmas.egbosi.photosapp.data.remote.PixaBayApi
import cosmas.egbosi.photosapp.domain.model.PixaImage
import cosmas.egbosi.photosapp.domain.repository.ImageRepository
import cosmas.egbosi.photosapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AppRepository(
    private val pixabayApi: PixaBayApi,
    private val imageDao: ImageDao
) : ImageRepository {

    override fun getImages(name: String?): Flow<Resource<List<PixaImage>>> = flow {

        emit(Resource.Loading())

        // Read the data in our cache
        val images = imageDao.getImages(name).map { it.toImage() }
        emit(Resource.Loading(data = images))

        try {

            // Get our words anc replace them in the database
            val remoteImages = pixabayApi.searchImages(name)
            imageDao.deleteImages(remoteImages.hits.map { it.previewURL })
            imageDao.insertImages(remoteImages.hits.map { it.toImageEntity() })

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = images
                )
            )

        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection!",
                    data = images
                )
            )

        }

        // Emit our data to the UI
        val newImages = imageDao.getImages(name).map { it.toImage() }
        emit(Resource.Success(newImages))
    }
}