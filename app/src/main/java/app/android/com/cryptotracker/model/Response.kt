package app.android.com.cryptotracker.model

data class Item(val id: String,
                val name: String,
                val symbol: String,
                val rank: String,
                val price_usd: String,
                val price_btc: String,
                val volume_usd_24h: String,
                val market_cap_usd: String,
                val total_supply: String,
                val max_supply: String?,
                val percent_change_1h: String,
                val percent_change_24h: String,
                val percent_change_7d: String,
                val last_updated: String)