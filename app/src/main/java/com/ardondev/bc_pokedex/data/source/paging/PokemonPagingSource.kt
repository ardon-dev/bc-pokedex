package com.ardondev.bc_pokedex.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ardondev.bc_pokedex.data.source.remote.ApiService
import com.ardondev.bc_pokedex.domain.model.error.traceErrorException
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
                limit = 1302,
                offset = currentPage
            )
            val list = pokemonListResponse.results
            if (list != null) {
                val prevKey = if (currentPage == 0) null else (currentPage - 1302)
                val nextKey = if (list.isEmpty()) null else (currentPage + 1302)
                LoadResult.Page(
                    data = list.map { it.toModel() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(NullPointerException())
            }
        } catch (e: IOException) {
            LoadResult.Error(traceErrorException(e))
        } catch (e: HttpException) {
            LoadResult.Error(traceErrorException(e))
        } catch (e: Exception) {
            LoadResult.Error(traceErrorException(e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

}