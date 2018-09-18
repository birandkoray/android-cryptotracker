package app.android.com.cryptotracker.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.android.com.cryptotracker.Item
import app.android.com.cryptotracker.R
import kotlinx.android.synthetic.main.crypto_item.view.*

class ListAdapter(private val list: List<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.crypto_item,
                        parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepo(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindRepo(crypto: Item) {
            with(crypto) {
                itemView.textView.text = crypto.name
            }
        }
    }

}