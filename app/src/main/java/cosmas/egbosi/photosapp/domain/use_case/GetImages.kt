package cosmas.egbosi.photosapp.domain.use_case

import cosmas.egbosi.photosapp.domain.model.PixaImage
import cosmas.egbosi.photosapp.domain.repository.ImageRepository
import cosmas.egbosi.photosapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetImages(
    private val repository: ImageRepository
) {

    operator fun invoke(name: String): Flow<Resource<List<PixaImage>>> {
        return repository.getImages(name)
    }
}