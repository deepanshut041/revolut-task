package `in`.deepanshut041.revolut.rates.adapter

import `in`.deepanshut041.revolut.domain.model.RateModel
import `in`.deepanshut041.revolut.rates.R
import `in`.deepanshut041.revoult.util.customAfterTextChanged
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.item_rates_row.view.*
import java.net.URL

class RateListAdapter(private val context: Context,
    val events: MutableLiveData<RateListEvent> = MutableLiveData()) :
    ListAdapter<RateModel, RateListViewHolder>(RateDiffUtilCallback()) {

    override fun onBindViewHolder(holder: RateListViewHolder, position: Int) {
        holder.bind(getItem(position), context)

        holder.itemContainer.setOnClickListener {
            if (position > 0)
                events.value = RateListEvent.onBaseChanged(getItem(position).name)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: RateListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

            (payloads[0] as Pair<RateModel, RateModel>).let {
                if (it.first.name != it.second.name) {
                    holder.bind(it.second, context)
                }
                else
                    holder.changeValue(it.second.value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateListViewHolder {
        return RateListViewHolder.create(parent)
    }
}

class RateListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): RateListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_rates_row, parent, false)
            return RateListViewHolder(view)
        }
    }

    private val itemImage: ImageView = view.ivRatesImage
    private val itemName: TextView = view.txtRatesName
    private val itemFullName: TextView = view.txtRatesFullName
    val itemValue: EditText = view.edtRatesValue
    val itemContainer: ConstraintLayout = view.clRateItemContainer

    fun bind(item: RateModel, context: Context) {
        itemName.text = item.name
        itemFullName.text = item.fullName
        changeValue(item.value)
        setSvg(context, item.imgUrl)
    }

    fun setSvg(context: Context, url: String){
        GlideToVectorYou
            .init()
            .with(context)
            .load(Uri.parse(url),  itemImage)
    }

    fun changeValue(v: Double) {
        val vString = if (v > 0.0) String.format("%.3f", v) else ""
        itemValue.setText(vString)
    }
}
