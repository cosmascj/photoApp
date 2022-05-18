package cosmas.egbosi.photosapp.ui.images

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cosmas.egbosi.photosapp.R
import cosmas.egbosi.photosapp.databinding.FragmentImagesBinding
import cosmas.egbosi.photosapp.util.Resource
import cosmas.egbosi.photosapp.util.hideKeyboard
import cosmas.egbosi.photosapp.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Images : Fragment() {
   private val viewModel: ImagesViewModel by viewModels()
    private lateinit var adapter: ImagesAdapter
    private lateinit var binding: FragmentImagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.searchQuery.value?.let { setUpObserver(it) }
        adapter = ImagesAdapter(ImagesAdapter.OnClickListener { image ->


            val action = ImagesDirections.actionImagesFragmentToImageDetailsFragment(image)
            findNavController().navigate(action)
        })

        binding.searchView.setEndIconOnClickListener {
            setUpObserver(binding.searchView.editText!!.text.toString())
            binding.imagesProgressBar.isVisible = true
            hideKeyboard()
        }

        return view
    }

private fun setUpObserver(searchString: String){
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.onSearch(searchString.trim()).collect { result ->
            when(result){
                is Resource.Success -> {
                    binding.imagesProgressBar.isVisible = false
                    binding.imagesRecyclerview.isVisible = true

                    if (result.data?.isEmpty()!!){
                        showSnackbar("No photos found")
                    } else {
                        adapter.submitList(result.data)
                        binding.imagesRecyclerview.adapter = adapter
                    }
                }
                is Resource.Error -> {
                    binding.imagesProgressBar.isVisible = true
                    binding.imagesRecyclerview.isVisible = false
                    showSnackbar(result.message ?: "Unknown Error!")
                }
                is Resource.Loading -> {
                    binding.imagesProgressBar.isVisible = true
                }
            }

        }
    }
}


}