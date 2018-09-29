package app.android.com.cryptotracker.constants

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import app.android.com.cryptotracker.R

object DialogUtil {

    fun logoutConfirmDialog(context: Context,
                                    positiveButtonId : Int,
                                    positiveButtonAction: DialogInterface.OnClickListener,
                                    negativeButtonId: Int,
                                    negativeButtonAction: DialogInterface.OnClickListener?
                                    ): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.logoutconfirm)
                .setPositiveButton(positiveButtonId,positiveButtonAction)
                .setNegativeButton(negativeButtonId,negativeButtonAction)
        // Create the AlertDialog object and return it
        return builder.create()
    }
}