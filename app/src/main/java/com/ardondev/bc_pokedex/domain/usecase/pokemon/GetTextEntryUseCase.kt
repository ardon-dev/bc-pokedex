package com.ardondev.bc_pokedex.domain.usecase.pokemon

import android.util.Log
import com.ardondev.bc_pokedex.domain.model.error.traceErrorException
import com.ardondev.bc_pokedex.domain.repository.PokemonRepository
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GetTextEntryUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {

    suspend operator fun invoke(id: Int): Result<String> {
        return try {
            val result = pokemonRepository.getPokemonSpecies(id)

            //Get first element in flavor text entries with language 'es'
            val textEntry =
                result.flavorTextEntries?.firstOrNull { it.language?.name == "es" }?.flavorText

            if (textEntry != null) {
                //Return found text
                Result.success(textEntry.replace("\n", " "))
            } else {
                //Return exception if not found
                Result.failure(NoSuchElementException())
            }
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