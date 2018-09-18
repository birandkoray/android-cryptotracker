package app.android.com.cryptotracker

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import app.android.com.cryptotracker.adapter.ListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class RestActivity : AppCompatActivity() {

    var itemList: List<Item> = ArrayList<Item>()

    var recyclerView: RecyclerView? = null

    var progressBar: ProgressBar? = null

    object Constant {
        val apiBaseURL = "https://api.coinmarketcap.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        recyclerView = findViewById(R.id.my_recycler_view)
        progressBar = findViewById(R.id.pBar)
        progressBar?.visibility = View.VISIBLE
        recyclerView?.layoutManager = LinearLayoutManager(this)
        if (isNetworkAvailable()) restCall()
        else {
            progressBar?.visibility = View.GONE; Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        }
    }

    fun restCall() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(Constant.apiBaseURL + "/v1/ticker/?limit=10")
                .build()


        client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("SALMAN", "initRecyclerView : init recyclerview.")
                        val body = response.body()?.string()
                        val gson = GsonBuilder().create()
                        itemList = gson.fromJson<List<Item>>(body, object : TypeToken<List<Item>>() {}.type)
                        runOnUiThread {
                            recyclerView?.adapter = ListAdapter(this@RestActivity, itemList)
                            progressBar?.visibility = View.GONE
                        }
                    }

                })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
