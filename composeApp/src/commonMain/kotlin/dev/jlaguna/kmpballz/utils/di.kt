package dev.jlaguna.kmpballz.utils

import dev.jlaguna.kmpballz.data.remote.CharacterService
import dev.jlaguna.kmpballz.data.repositories.CharactersRepository
import dev.jlaguna.kmpballz.ui.scenes.characterDetail.CharacterDetailViewModel
import dev.jlaguna.kmpballz.ui.scenes.charactersList.CharactersListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(dataModule, viewModelsModule)
    }
}

val dataModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "dragonball-api.com/api"
                }
            }
        }
    }

    factoryOf(::CharacterService)

    factoryOf(::CharactersRepository)
}

val viewModelsModule = module {
    factoryOf(::CharactersListViewModel)
    factoryOf(::CharacterDetailViewModel)
}
