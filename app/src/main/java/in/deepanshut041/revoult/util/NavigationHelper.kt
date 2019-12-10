package `in`.deepanshut041.revoult.util

import android.content.Context
import android.content.Intent

object NavigationHelper {

    fun navigateToRate(context: Context): Intent? {
        return Intent().setClassName(context, "in.deepanshut041.revolut.rates.RatesActivity")
    }
}