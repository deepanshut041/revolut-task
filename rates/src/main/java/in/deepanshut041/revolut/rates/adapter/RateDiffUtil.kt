package `in`.deepanshut041.revolut.rates.adapter

import `in`.deepanshut041.revolut.domain.model.RateModel
import androidx.recyclerview.widget.DiffUtil

class RateDiffUtilCallback: DiffUtil.ItemCallback<RateModel>(){

    override fun areItemsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RateModel, newItem: RateModel): Boolean {
        return oldItem.value == newItem.value

    }

    override fun getChangePayload(oldItem: RateModel, newItem: RateModel): Pair<RateModel, RateModel> {
        return Pair(oldItem, newItem)
    }
}
