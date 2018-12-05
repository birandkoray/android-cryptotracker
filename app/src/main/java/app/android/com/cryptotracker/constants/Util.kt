package app.android.com.cryptotracker.constants

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat

object Util {

    val NOT_FOUND = 0

    val POSITIVE_COLOR = "#00ff00"

    val NEGATIVE_COLOR = "#ff0000"

    fun getLogo(resource: Resources , name : String , packageName : String ,
                unknownLogo : Int) : Drawable {
        var resId = resource.getIdentifier(name , "drawable" , packageName )
        if (resId === NOT_FOUND) resId = unknownLogo
        return ResourcesCompat.getDrawable(resource , resId , null)!!
    }

    fun decideColor(value : Double) : String {
        var result = POSITIVE_COLOR
        if (value < 0)
            result = NEGATIVE_COLOR
        return result
    }

    fun formatWithSymbol(value : Any , symbol : String = "%") : String {
        return "%.2f".format(value) + symbol
    }

}