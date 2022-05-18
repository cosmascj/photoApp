package cosmas.egbosi.photosapp.ui.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cosmas.egbosi.photosapp.R
import cosmas.egbosi.photosapp.databinding.ImageRowBinding
import cosmas.egbosi.photosapp.domain.model.PixaImage


class ImagesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PixaImage, ImagesAdapter.PhotoViewHolder>(
        PHOTO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        return PhotoViewHolder(
            ImageRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(image)
        }
    }

    class PhotoViewHolder(private val binding: ImageRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: PixaImage?) {
            Glide.with(binding.image)
                .load(image?.webformatURL)
                .placeholder(R.drawable.gallery)
                .into(binding.image)
            binding.ownerName.text = image?.user
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PixaImage>() {
            override fun areItemsTheSame(oldItem: PixaImage, newItem: PixaImage): Boolean {
                return oldItem.previewURL == newItem.previewURL

            }

            override fun areContentsTheSame(oldItem: PixaImage, newItem: PixaImage): Boolean {
                return oldItem == newItem

            }
        }
    }

    class OnClickListener(val clickListener: (image: PixaImage) -> Unit) {
        fun onClick(image: PixaImage) = clickListener(image)
    }


}