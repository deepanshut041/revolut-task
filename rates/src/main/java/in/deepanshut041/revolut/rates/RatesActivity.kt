package `in`.deepanshut041.revolut.rates

import `in`.deepanshut041.revolut.rates.adapter.RateListAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_rates.*
import kotlinx.android.synthetic.main.view_rates_error.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class RatesActivity : AppCompatActivity() {

    // Loading Auth Module
    private val loadFeatures by lazy { loadKoinModules(ratesModule) }

    private fun injectFeatures() = loadFeatures

    // Instantiate viewModel with Koin
    private val viewModel by viewModel<RateViewModel>()

    // ListAdapter
    private lateinit var listAdapter: RateListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        injectFeatures()
        setRecyclerView()
        bindViewModel()

    }

    private fun setRecyclerView() {
        listAdapter = RateListAdapter(this)
        rvRatesList.layoutManager = LinearLayoutManager(this)
        rvRatesList.adapter = listAdapter

        listAdapter.events.observe(this, Observer {
            viewModel.handleEvent(it)
        })

        btnRatesRetry.setOnClickListener {
            viewModel.getRates()
        }
    }

    private fun bindViewModel() {
        with(viewModel) {
            getRates()

            rates.observe(this@RatesActivity, Observer {
                listAdapter.submitList(it)

            })

            screenEvents.observe(this@RatesActivity, Observer {
                when (it.code) {
                    RateViewModel.ScreenEvent.SHOW_ERROR_VIEW -> {
                        rvRatesList.visibility = View.INVISIBLE
                        vRateError.visibility = View.VISIBLE
                        pbRatesLoader.visibility = View.INVISIBLE
                    }
                    RateViewModel.ScreenEvent.SHOW_PROGRESS_BAR -> {
                        rvRatesList.visibility = View.INVISIBLE
                        vRateError.visibility = View.INVISIBLE
                        pbRatesLoader.visibility = View.VISIBLE
                    }
                    RateViewModel.ScreenEvent.SHOW_RECYCLER_VIEW -> {
                        listAdapter.notifyDataSetChanged()
                        rvRatesList.visibility = View.VISIBLE
                        vRateError.visibility = View.INVISIBLE
                        pbRatesLoader.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(ratesModule)
    }
}