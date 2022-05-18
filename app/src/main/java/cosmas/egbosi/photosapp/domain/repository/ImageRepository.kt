package cosmas.egbosi.photosapp.domain.repository

import cosmas.egbosi.photosapp.domain.model.PixaImage
import cosmas.egbosi.photosapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(name: String?): Flow<Resource<List<PixaImage>>>
}