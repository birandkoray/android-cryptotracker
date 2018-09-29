package app.android.com.cryptotracker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import app.android.com.cryptotracker.constants.Constants
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener { view ->
            signIn(view, Constants.EMAIL , Constants.PASSWORD)
        }

    }

    fun signIn(view: View, email: String,
               password: String) {

        showMessage(view, "Authenticating...")

        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var intent = Intent(this, RestActivity::class.java)
                        intent.putExtra("id", fbAuth.currentUser?.email)
                        startActivity(intent)
                    } else {
                        showMessage(view, "Error: ${task.exception?.message}")
                    }
                }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
    }

}