package `in`.deepanshut041.revoult.ui

import `in`.deepanshut041.revoult.util.NavigationHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        finish()
        startActivity(NavigationHelper.navigateToRate(this))
    }

}
