package app.android.com.cryptotracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import app.android.com.cryptotracker.constants.Constants
import app.android.com.cryptotracker.constants.Util
import app.android.com.cryptotracker.model.Item

class DetailActivity : AppCompatActivity() {

    val item: Item by lazy { intent.extras[Constants.ITEM] as Item }

    val logo: ImageView by lazy { findViewById<ImageView>(R.id.crypto_logo) }

    val name: TextView by lazy { findViewById<TextView>(R.id.crypto_name) }

    val price: TextView by lazy { findViewById<TextView>(R.id.crypto_price) }

    val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.my_toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
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
        logo.setImageDrawable(Util.getLogo(this.resources, item.symbol.toLowerCase(), this.packageName, R.drawable.unknown))
    }

    private fun customizeDetail() {
        name.text = item.name
        price.text = Util.formatWithSymbol(item.quote.USD.price, "$")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true
    }
}
