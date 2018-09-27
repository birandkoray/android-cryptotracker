package app.android.com.cryptotracker

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import app.android.com.cryptotracker.adapter.ListAdapter
import app.android.com.cryptotracker.constants.Constants
import app.android.com.cryptotracker.model.Item
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class RestActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.my_recycler_view) }

    private val mSwipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.my_swipe) }

    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.pBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        prepareSwipeLayout()
        prepareRecyclerView()
        progressBar.visibility = View.VISIBLE
        if (isNetworkAvailable()) restCall()
        else {
            progressBar.visibility = View.GONE; Toast.makeText(this, Constants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun prepareSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun restCall() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(Constants.APIURL)
                .build()


        client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body()?.string()
                        val gson = GsonBuilder().create()
                        val itemList = gson.fromJson<List<Item>>(body, object : TypeToken<List<Item>>() {}.type)
                        runOnUiThread {
                            recyclerView.adapter = ListAdapter(this@RestActivity, itemList)
                            progressBar.visibility = View.GONE
                            mSwipeRefreshLayout.isRefreshing = false

                        }
                    }

                })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onRefresh() {
        restCall()
    }
}
