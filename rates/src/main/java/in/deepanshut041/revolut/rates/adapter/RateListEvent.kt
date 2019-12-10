package `in`.deepanshut041.revolut.rates.adapter

sealed class RateListEvent {
    data class onBaseChanged(val baseChanged: String): RateListEvent()
    data class onBaseValueChanged(val value: Double): RateListEvent()
}
