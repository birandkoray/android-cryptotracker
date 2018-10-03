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
import app.android.com.cryptotracker.MainActivity
import app.android.com.cryptotracker.model.Item
import app.android.com.cryptotracker.R
import app.android.com.cryptotracker.RestActivity
import app.android.com.cryptotracker.constants.Constants
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
        holder?.item = list[position]
    }


    class ViewHolder(view: View , var item : Item? = null) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val intent = Intent(view.context , DetailActivity::class.java)
                intent.putExtra(Constants.ITEM , item)
                view.context.startActivity(intent)
            }
        }


        fun bindRepo(crypto: Item, ctx: Context) {


            itemView.name.text = crypto.name

            var percent24Change = "#006400"

            var percent7Change = "#006400"

            if (crypto.percent_change_24h.toFloat() < 0) percent24Change = "#ff0000"

            itemView.percent_24h_val.text = crypto.percent_change_24h + '%'

            itemView.percent_24h_val.setTextColor(Color.parseColor(percent24Change));

            if(crypto.percent_change_7d.toFloat() < 0) percent7Change = "#ff0000"

            itemView.percent_7d_val.text = crypto.percent_change_7d + '%'

            itemView.percent_7d_val.setTextColor(Color.parseColor(percent7Change))

            val resId = ctx.resources.getIdentifier(crypto.symbol.toLowerCase(), "drawable", ctx.packageName)

            val drawable: Drawable? = ResourcesCompat.getDrawable(ctx.resources, resId, null)

            itemView.imageView.setImageDrawable(drawable)

        }
    }

}