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


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.crypto_item,
                        parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepo(list[position], ctx)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindRepo(crypto: Item, ctx: Context) {

            val picasso = Picasso.with(ctx)
            itemView.textView.text = crypto.name
            picasso.load(ctx.resources.getString(ctx.resources.getIdentifier(crypto.symbol,"string" , ctx.packageName)))
                    .into(itemView.imageView)
        }
    }

}