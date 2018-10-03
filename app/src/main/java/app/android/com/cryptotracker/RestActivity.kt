package app.android.com.cryptotracker

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import app.android.com.cryptotracker.adapter.ListAdapter
import app.android.com.cryptotracker.constants.Constants
import app.android.com.cryptotracker.constants.DialogUtil
import app.android.com.cryptotracker.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

class RestActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    var fbAuth = FirebaseAuth.getInstance()

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.my_recycler_view) }

    private val mSwipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.my_swipe) }

    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.pBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        prepareSwipeLayout()
        prepareRecyclerView()
        progressBar.visibility = View.VISIBLE
        if (isNetworkAvailable()) restCall()
        else {
            progressBar.visibility = View.GONE; Toast.makeText(this, Constants.NETWORK_ERROR_MSG, Toast.LENGTH_LONG).show()
        }

        fbAuth.addAuthStateListener {
            if (fbAuth.currentUser == null) {
                this.finish()
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout2 -> {
            Toast.makeText(this, "LOGGING OUT", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_resource, menu)
        val menuItem = menu.findItem(R.id.logout)
        val logoutBtn = menuItem.actionView.findViewById<Button>(R.id.logout_button)
        logoutBtn.setOnClickListener { view ->
            DialogUtil.logoutConfirmDialog(this, R.string.yes, DialogInterface.OnClickListener { dialogInterface, i ->
                fbAuth.signOut()
                Toast.makeText(this, "LOGGED OUT", Toast.LENGTH_SHORT).show()
            }, R.string.no, null).show()
        }
        return true
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
