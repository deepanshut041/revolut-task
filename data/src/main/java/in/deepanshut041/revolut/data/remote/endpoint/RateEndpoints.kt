package `in`.deepanshut041.revolut.data.remote.endpoint

import `in`.deepanshut041.revolut.data.remote.dto.RateResponseDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RateEndpoints {

    @GET("/latest")
    fun fetchRates(@Query("base") base: String): Observable<RateResponseDto>
}