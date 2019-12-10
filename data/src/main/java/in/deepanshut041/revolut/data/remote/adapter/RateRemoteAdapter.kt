package `in`.deepanshut041.revolut.data.remote.adapter

import `in`.deepanshut041.revolut.data.remote.dto.RateDto
import `in`.deepanshut041.revolut.data.remote.endpoint.RateEndpoints
import `in`.deepanshut041.revolut.domain.model.RateModel
import io.reactivex.Observable

class RateRemoteAdapterImpl(private val remote: RateEndpoints) : RateRemoteAdapter {

    override fun loadCurrencyRate(base: String,  baseValue: Double): Observable<List<RateModel>> {
        return remote.fetchRates(base).map { response ->
            val rates = ArrayList<RateModel>()
            rates.add(RateModel(base, RateDto.valueOf(base).fullName, baseValue,  getFlagUrl(base)))
            response.rates.map {
                rates.add(RateModel(it.key.name, it.key.fullName, it.value * baseValue,  getFlagUrl(it.key.name)))
            }
            rates.toList()
        }
    }

    private fun getFlagUrl(name:String) = "https://www.xe.com/themes/xe/images/flags/svg/${name.toLowerCase()}.svg"

}

interface RateRemoteAdapter {
    fun loadCurrencyRate(base: String, baseValue: Double): Observable<List<RateModel>>
}
