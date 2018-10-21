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
        drawLogo()
        name.setText(item.name)
        price.setText(item.price_usd.substring(0 , item.price_usd.indexOf('.') + 3) + '$')

    }

    private fun drawLogo() {
        val resId = this.resources.getIdentifier(item.symbol.toLowerCase(), "drawable", this.packageName)
        val drawable: Drawable? = ResourcesCompat.getDrawable(this.resources, resId, null)
        logo.setImageDrawable(drawable)
    }
}
