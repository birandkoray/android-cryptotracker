package app.android.com.cryptotracker.constants

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v4.content.ContextCompat
import app.android.com.cryptotracker.R

object DialogUtil {

    fun logoutConfirmDialog(context: Context,
                            positiveButtonId: Int,
                            positiveButtonAction: DialogInterface.OnClickListener,
                            negativeButtonId: Int,
                            negativeButtonAction: DialogInterface.OnClickListener?
    ): Dialog {
        val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
        builder.setMessage(R.string.logoutconfirm)
                .setPositiveButton(positiveButtonId, positiveButtonAction)
                .setNegativeButton(negativeButtonId, negativeButtonAction)
        // Create the AlertDialog object and return it
        val alertDialog = builder.create()

        alertDialog.setOnShowListener {
            val dialog = it as AlertDialog
            dialog.window.decorView.setBackgroundColor(Color.YELLOW)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4fb81c"))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);

        }

        return alertDialog
    }
}