package app.android.com.cryptotracker.fragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import app.android.com.cryptotracker.MainActivity
import app.android.com.cryptotracker.R
import app.android.com.cryptotracker.constants.DialogUtil
import com.google.firebase.auth.FirebaseAuth

class ToolbarFragment : Fragment() {

    var fbAuth = FirebaseAuth.getInstance()

    companion object {
        fun newInstance(): ToolbarFragment {
            return ToolbarFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.toolbar_layout,
                container, true) as View

        fbAuth.addAuthStateListener {
            if (fbAuth.currentUser == null) {
                activity?.finish()
            }
        }

        val logoutBtn = view.findViewById<Button>(R.id.logout_button)
        logoutBtn.setOnClickListener { view ->
            DialogUtil.logoutConfirmDialog(view.context, R.string.yes, DialogInterface.OnClickListener { dialogInterface, i ->
                fbAuth.signOut()
                Toast.makeText(view.context, "LOGGED OUT", Toast.LENGTH_SHORT).show()
            }, R.string.no, null).show()
        }

        return view
    }
}