package cosmas.egbosi.photosapp.ui.image_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import cosmas.egbosi.photosapp.domain.model.PixaImage
import javax.inject.Inject

class ImageDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel(){
    private val _imageDetails = MutableLiveData(savedStateHandle.get<PixaImage>("imageDetails"))
    val imageDetails: LiveData<PixaImage?> = _imageDetails
}