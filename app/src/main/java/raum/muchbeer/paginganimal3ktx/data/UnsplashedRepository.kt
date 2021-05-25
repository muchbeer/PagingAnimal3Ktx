package raum.muchbeer.paginganimal3ktx.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import raum.muchbeer.paginganimal3ktx.api.UnsplashedService
import javax.inject.Inject

class UnsplashedRepository @Inject constructor(private val unsplashApi : UnsplashedService) {

    fun getPhotoSearch(search : String) {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashedPagingSource(unsplashApi, search) }
        ).liveData
    }
}