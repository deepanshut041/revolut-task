package `in`.deepanshut041.revolut.data.remote.dto

data class RateResponseDto(
    val base: RateDto,
    var rates: Map<RateDto, Double>
)
