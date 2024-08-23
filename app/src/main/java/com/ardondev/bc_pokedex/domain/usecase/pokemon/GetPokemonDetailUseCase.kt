package com.ardondev.bc_pokedex.domain.usecase.pokemon

import com.ardondev.bc_pokedex.domain.model.error.traceErrorException
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

    suspend operator fun invoke(id: Int): Result<Pokemon> {
        return try {
            Result.success(pokemonRepository.getPokemonDetail(id).toModel())
        } catch (e: IOException) {
            Result.failure(traceErrorException(e))
        } catch (e: HttpException) {
            Result.failure(traceErrorException(e))
        } catch (e: Exception) {
            Result.failure(traceErrorException(e))
        } catch (e: SocketTimeoutException) {
            Result.failure(traceErrorException(e))
        } catch (e: UnknownHostException) {
            Result.failure(traceErrorException(e))
        }
    }

}