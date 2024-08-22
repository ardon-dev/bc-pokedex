package com.ardondev.bc_pokedex.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ardondev.bc_pokedex.data.source.remote.ApiService
import com.ardondev.bc_pokedex.data.source.remote.response.pokemon.toModel
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val apiService: ApiService,
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val currentPage = params.key ?: 0
            val pokemonListResponse = apiService.getPokemonList(
                limit = 10,
                offset = currentPage
            )
            val list = pokemonListResponse.results
            if (list != null) {
                val prevKey = if (currentPage == 0) null else (currentPage - 10)
                val nextKey = if (list.isEmpty()) null else (currentPage + 10)
                LoadResult.Page(
                    data = list.map { it.toModel() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(NullPointerException())
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }

}