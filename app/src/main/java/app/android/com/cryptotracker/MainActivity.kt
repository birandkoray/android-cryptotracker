package app.android.com.cryptotracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import app.android.com.cryptotracker.constants.Constants
import com.google.firebase.auth.FirebaseAuth

fun EditText.afterTextChange(func: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            func.invoke(p0.toString())
        }

    })
}

class MainActivity : AppCompatActivity() {

    private val passwordField by lazy { findViewById<EditText>(R.id.passfield) }

    private val btnLogin by lazy { findViewById<Button>(R.id.btnLogin) }

    private val fbAuth = FirebaseAuth.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin.setOnClickListener { view ->
            signIn(view, Constants.EMAIL, Constants.PASSWORD)
        }

        passwordField.afterTextChange { signIn(it) }


    }

    private fun signIn(view: View, email: String,
                       password: String) {

        showMessage(view, "Authenticating...")

        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        passwordField.setText("")
                        val intent = Intent(this, RestActivity::class.java)
                        intent.putExtra("id", fbAuth.currentUser?.email)
                        startActivity(intent)
                    } else {
                        showMessage(view, "Error: ${task.exception?.message}")
                    }
                }
    }


    private fun signIn(password: String) {
        if (password.length >= 6 && password.contentEquals(Constants.NUMBER)) {
            signIn(window.decorView.rootView, Constants.EMAIL, password)
        }
    }

    private fun showMessage(view: View, message: String) {
        val sb = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Action", null)
        val sbView = sb.view
        sbView.setBackgroundColor(Color.parseColor("#80c342"))
        sb.show()
    }

}