package app.android.com.cryptotracker.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.android.com.cryptotracker.Item
import app.android.com.cryptotracker.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_item.view.*

class ListAdapter(val ctx: Context, private val list: List<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    val context = ctx

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.crypto_item,
                        parent, false)
        return ViewHolder(view, ctx)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepo(list[position])
    }

    class ViewHolder(view: View, ctx: Context) : RecyclerView.ViewHolder(view) {

        val picasso = Picasso.with(ctx)

        fun bindRepo(crypto: Item) {

            itemView.textView.text = crypto.name
            picasso.load("https://res.cloudinary.com/da7jhtpgh/image/upload/v1508609483/bitcoin_eqld4v.png")
                    .into(itemView.imageView)
        }
    }

}