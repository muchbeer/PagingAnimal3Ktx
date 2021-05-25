package raum.muchbeer.paginganimal3ktx.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import raum.muchbeer.paginganimal3ktx.api.UnsplashedService
import raum.muchbeer.paginganimal3ktx.model.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException


private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashedPagingSource(private val unsplashApi: UnsplashedService,
                             private val query: String) : PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (http: HttpException) {
            LoadResult.Error(http)
        }
    }


}