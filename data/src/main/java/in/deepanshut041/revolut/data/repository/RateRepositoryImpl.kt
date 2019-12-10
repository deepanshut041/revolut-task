package `in`.deepanshut041.revolut.data.repository

import `in`.deepanshut041.revolut.data.remote.adapter.RateRemoteAdapter
import `in`.deepanshut041.revolut.domain.model.RateModel
import `in`.deepanshut041.revolut.domain.repository.RateRepository
import io.reactivex.Observable
import io.reactivex.Single

class RateRepositoryImpl(private val remoteAdapter: RateRemoteAdapter): RateRepository {

    override fun loadRates(base: String): Observable<List<RateModel>> {
        return remoteAdapter.loadCurrencyRate(base)
    }

}