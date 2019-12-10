package `in`.deepanshut041.revolut.domain.repository

import `in`.deepanshut041.revolut.domain.model.RateModel
import io.reactivex.Observable
import io.reactivex.Single

interface RateRepository {
    fun loadRates(base:String): Observable<List<RateModel>>
}