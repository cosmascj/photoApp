package cosmas.egbosi.photosapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>)

    @Query("DELETE FROM imageentity WHERE previewURL IN(:images)")
    suspend fun deleteImages(images: List<String>)

    @Query("SELECT * FROM imageentity WHERE tags OR previewURL OR pageURL LIKE '%' || :query || '%'")
    suspend fun getImages(query: String?): List<ImageEntity>
}