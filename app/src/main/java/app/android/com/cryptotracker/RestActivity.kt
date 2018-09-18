package app.android.com.cryptotracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import app.android.com.cryptotracker.adapter.ListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_rest.*
import okhttp3.*
import java.io.IOException

class RestActivity : AppCompatActivity() {

    var itemList: List<Item> = ArrayList<Item>()

    object Constant {
        val apiBaseURL = "https://api.coinmarketcap.com"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        run()
        Thread.sleep(2000)
        recyclerView.adapter = ListAdapter(this,itemList)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun run() {
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
                        Log.d("SALMAN" , "initRecyclerView : init recyclerview.")
                        val body = response.body()?.string()
                        val gson = GsonBuilder().create()
                        itemList = gson.fromJson<List<Item>>(body , object : TypeToken<List<Item>>() {}.type)
                    }

                })
    }
}
