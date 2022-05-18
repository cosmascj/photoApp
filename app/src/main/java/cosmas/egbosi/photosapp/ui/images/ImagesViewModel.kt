package cosmas.egbosi.photosapp.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import cosmas.egbosi.photosapp.domain.model.PixaImage

import cosmas.egbosi.photosapp.domain.use_case.GetImages
import cosmas.egbosi.photosapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val getImages: GetImages): ViewModel(){

    private val _searchQuery = MutableLiveData("fruits")
    val searchQuery: LiveData<String?> = _searchQuery
    fun onSearch(query: String): Flow<Resource<List<PixaImage>>> {
        _searchQuery.value = query
        return getImages(query)
    }
}