package `in`.deepanshut041.revolut.rates

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ratesModule = module {

    viewModel {
        RateViewModel(get())
    }
}