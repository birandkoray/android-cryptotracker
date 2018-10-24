package app.android.com.cryptotracker.model

import java.io.Serializable


data class Response(val status: Status,
                    val data: List<Item>) : Serializable


data class Status(val timestamp: String,
                  val error_code: Int,
                  val error_message: String?,
                  val elapsed: Int,
                  val credit_count: Int
) : Serializable


data class Item(val id: String,
                val name: String,
                val symbol: String,
                val slug: String,
                val circulating_supply: Double,
                val max_supply: String?,
                val date_added: String,
                val num_market_pairs: Int,
                val cmc_rank: Int,
                val last_updated: String,
                val quote: quote) : Serializable

data class quote(val USD : USD) : Serializable

data class USD(val price: Double,
               val volume_24h: Double,
               val percent_change_1h: Double,
               val percent_change_24h: Double,
               val percent_change_7d: Double,
               val market_cap : Double,
               val last_updated: String) : Serializable