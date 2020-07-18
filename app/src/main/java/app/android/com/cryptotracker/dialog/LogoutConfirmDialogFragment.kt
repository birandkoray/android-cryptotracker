package app.android.com.cryptotracker.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import app.android.com.cryptotracker.R

class LogoutConfirmDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.logoutconfirm)
                    .setPositiveButton(R.string.yes,
                            DialogInterface.OnClickListener { _, _ ->
                                // FIRE ZE MISSILES!
                            })
                    .setNegativeButton(R.string.no,
                            DialogInterface.OnClickListener { _, _ ->
                                // User cancelled the dialog
                            })
            // Create the AlertDialog object and return it
            val dialog  : AlertDialog = builder.create()

            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}