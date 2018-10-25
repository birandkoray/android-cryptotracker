package app.android.com.cryptotracker.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.android.com.cryptotracker.DetailActivity
import app.android.com.cryptotracker.R
import app.android.com.cryptotracker.constants.Constants
import app.android.com.cryptotracker.model.Item
import kotlinx.android.synthetic.main.crypto_item.view.*

class ListAdapter(val ctx: Context, private val list: List<Item>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var selectedRowIndex: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.crypto_item,
                        parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepo(list[position], ctx)
        if (selectedRowIndex == position) {
            holder?.itemView!!.setBackgroundColor(Color.YELLOW)
            holder.itemView.percent_change_7d.setTextColor(Color.BLACK)
            holder.itemView.percent_change_24h.setTextColor(Color.BLACK)
        } else {
            holder?.itemView!!.setBackgroundColor(Color.DKGRAY)
            holder.itemView.percent_change_7d.setTextColor(Color.WHITE)
            holder.itemView.percent_change_24h.setTextColor(Color.WHITE)
        }
        holder.item = list[position]
        holder.itemView.setOnClickListener {
            selectedRowIndex = position
            notifyDataSetChanged()
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(Constants.ITEM, holder?.item)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(view: View, var item: Item? = null) : RecyclerView.ViewHolder(view) {

        var percent24Change = "#00ff00"
        var percent7Change = "#00ff00"

        fun bindRepo(crypto: Item, ctx: Context) {
            itemView.name.text = crypto.name
            val percent_change_24h = crypto.quote.USD.percent_change_24h
            val percent_change_7d = crypto.quote.USD.percent_change_7d
            if (percent_change_24h < 0) percent24Change = "#ff0000"
            itemView.percent_24h_val.text = "%.2f".format(percent_change_24h) + '%'
            itemView.percent_24h_val.setTextColor(Color.parseColor(percent24Change));
            if (percent_change_7d < 0) percent7Change = "#ff0000"
            itemView.percent_7d_val.text = "%.2f".format(percent_change_7d) + '%'
            itemView.percent_7d_val.setTextColor(Color.parseColor(percent7Change))
            val resId = ctx.resources.getIdentifier(crypto.symbol.toLowerCase(), "drawable", ctx.packageName)
            val drawable: Drawable? = ResourcesCompat.getDrawable(ctx.resources, resId, null)
            itemView.imageView.setImageDrawable(drawable)
        }
    }
}