package `in`.deepanshut041.revolut.rates

import `in`.deepanshut041.revolut.domain.model.RateModel
import `in`.deepanshut041.revolut.domain.repository.RateRepository
import `in`.deepanshut041.revolut.rates.adapter.RateListEvent
import `in`.deepanshut041.revoult.util.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RateViewModel(private val rateRepository: RateRepository) : ViewModel() {

    val rates: LiveData<List<RateModel>>
        get() = _rates

    val screenEvents: LiveData<ScreenEvent>
        get() = _screenEvents

    private val disposable = CompositeDisposable()

    private val _rates = SingleLiveEvent<List<RateModel>>()
    private val _baseRate = MutableLiveData<String>()
    private val _baseRateValue = MutableLiveData<Double>()
    private val _screenEvents = SingleLiveEvent<ScreenEvent>()


    private val subscription = Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())


    init {
        _baseRate.value = "EUR"
        _baseRateValue.value = 1.0
    }


    fun handleEvent(it: RateListEvent) {
        when (it) {
            is RateListEvent.onBaseChanged -> {
                _baseRate.value = it.baseChanged
                _baseRateValue.value = 1.0
                _screenEvents.value = ScreenEvent(ScreenEvent.SHOW_PROGRESS_BAR)
            }

            is RateListEvent.onBaseValueChanged -> {
                _baseRateValue.value = it.value
                reviseValues(_rates.value!!)
            }
        }
    }

    fun getRates() {
        _screenEvents.value = ScreenEvent(ScreenEvent.SHOW_PROGRESS_BAR)
        disposable.add(
            subscription
                .flatMap { rateRepository.loadRates(_baseRate.value!!) }
                .repeatUntil { rates.hasActiveObservers().not() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (_screenEvents.value!!.code == ScreenEvent.SHOW_PROGRESS_BAR) {
                            _baseRateValue.value = 1.0
                            reviseValues(it, false)
                            _screenEvents.value = ScreenEvent(ScreenEvent.SHOW_RECYCLER_VIEW)
                        } else {
                            reviseValues(it, false)
                        }
                    },
                    {
                        _screenEvents.value = ScreenEvent(ScreenEvent.SHOW_ERROR_VIEW)
                    }
                )
        )
    }

    private fun reviseValues(values: List<RateModel>, oldRates: Boolean = true) {
        val newValues = ArrayList<RateModel>()
        val baseValue = values[0].value

        if (oldRates && baseValue != 0.0) {
            values.map {
                newValues.add(it.copy(value = _baseRateValue.value!!.times(it.value).div(baseValue)))
            }
        } else {
            values.map {
                newValues.add(it.copy(value = _baseRateValue.value!!.times(it.value)))
            }
        }

        this._rates.value = newValues
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    class ScreenEvent(var code: Int) {
        companion object {
            const val SHOW_PROGRESS_BAR = 0
            const val SHOW_RECYCLER_VIEW = 1
            const val SHOW_ERROR_VIEW = 2
        }
    }
}

