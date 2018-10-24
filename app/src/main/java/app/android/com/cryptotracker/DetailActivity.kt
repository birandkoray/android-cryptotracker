package app.android.com.cryptotracker

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import android.widget.TextView
import app.android.com.cryptotracker.constants.Constants
import app.android.com.cryptotracker.model.Item

class DetailActivity : AppCompatActivity() {

    val item: Item by lazy { intent.extras[Constants.ITEM] as Item }

    val logo: ImageView by lazy { findViewById<ImageView>(R.id.crypto_logo) }

    val name: TextView by lazy { findViewById<TextView>(R.id.crypto_name) }

    val price: TextView by lazy { findViewById<TextView>(R.id.crypto_price)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        customizeSupportActionBar()
        drawLogo()
        customizeDetail()
    }

    private fun customizeSupportActionBar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = item.name + " Detail"
    }

    private fun drawLogo() {
        val resId = this.resources.getIdentifier(item.symbol.toLowerCase(), "drawable", this.packageName)
        val drawable: Drawable? = ResourcesCompat.getDrawable(this.resources, resId, null)
        logo.setImageDrawable(drawable)
    }

    private fun customizeDetail() {
        name.text = item.name
        price.text = "%.2f".format(item.quote.USD.price) + "$"
    }
}
