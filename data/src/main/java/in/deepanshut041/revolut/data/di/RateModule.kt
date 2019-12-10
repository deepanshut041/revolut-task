package `in`.deepanshut041.revolut.data.di

import `in`.deepanshut041.revolut.data.remote.adapter.RateRemoteAdapter
import `in`.deepanshut041.revolut.data.remote.adapter.RateRemoteAdapterImpl
import `in`.deepanshut041.revolut.data.remote.endpoint.RateEndpoints
import `in`.deepanshut041.revolut.data.repository.RateRepositoryImpl
import `in`.deepanshut041.revolut.domain.repository.RateRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val rateDataModule = module {
    single(createdAtStart = false) {
        get<Retrofit>().create(RateEndpoints::class.java)
    }

    single<RateRemoteAdapter>(createdAtStart = false) {
        RateRemoteAdapterImpl(get())
    }

    single<RateRepository> {
        RateRepositoryImpl(get())
    }
}