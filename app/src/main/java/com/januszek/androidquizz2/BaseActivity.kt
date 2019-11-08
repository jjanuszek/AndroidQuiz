package com.januszek.androidquizz2

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

abstract class BaseActivity : AppCompatActivity() {

    private val baseAuthStateListener: FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            QApp.fUser = firebaseAuth.currentUser
        }
    }

    override fun onResume() {
        super.onResume()
        QApp.fAuth.addAuthStateListener(baseAuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        QApp.fAuth.removeAuthStateListener(baseAuthStateListener)

    }

    fun logIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        Log.d("BaseActivity", gso.toString())

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)

        Log.d("BaseActivity mG", mGoogleSignInClient.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val taks = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = taks.getResult(ApiException::class.java)

                val credential = GoogleAuthProvider.getCredential(account?.idToken, null) //todo dodałem ? przed idToken
                QApp.fAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, { task ->
                        if (task.isSuccessful) {
                            onLogInSuccess()
                        } else {
                            onLogInFailue(task.exception)
                        }
                    })
            } catch (e: ApiException) {
                Log.w("BASE_ACTIVITY", "Google sign in failed: ${e.statusCode}")
            }
        }
    }

    /**
     *PL Na nieudane zalogowanie w firebase odpalana jest ta metoda
     *PL Możesz ją nadpisać i obsłużyć albo przekazać dalej obsługę
     *EN On failed firebase login this method is called
     *EN You can override it and react or pass event
     */
    open fun onLogInFailue(exception: Exception?) {
        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        Log.d("BASE_ACTIVITY", "log in failed")
    }

    /**
     *PL Na zalogowanie w firebase odpalana jest ta metoda
     *PL Możesz ją nadpisać i obsłużyć albo przekazać dalej obsługę
     *EN On firebase login this method is called
     *EN You can override it and react or pass event
     */
    open fun onLogInSuccess() {
        Log.d("BASE_ACTIVITY", "log in success")
    }

    companion object {
        const val RC_SIGN_IN = 12412
    }
}