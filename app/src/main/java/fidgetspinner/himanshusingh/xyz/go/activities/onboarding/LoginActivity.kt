package xyz.himanshusingh.fidgetspinner.activities.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import xyz.himanshusingh.fidgetspinner.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.R.attr.data
import android.util.Log
import android.support.design.widget.Snackbar
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import fidgetspinner.himanshusingh.xyz.go.utility.Constants


class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001
    
    // [START declare_auth]
    private var mAuth: FirebaseAuth? = null
    // [END declare_auth]
    
    private var mGoogleSignInClient: GoogleSignInClient? = null
    
    companion object {
        fun sendToLoginActivity(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSetupGoogle()
    }
    
    @SuppressLint("RestrictedApi")
    private fun loginSetupGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.LOGIN_KEY)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        
        // [START initialize_auth]
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
    }
    
    fun GoogleLoginButton(view: View) = signIn()
    
    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
            }
            
        }
    }
    
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth!!.currentUser
                    }
                }
    }
    
    @SuppressLint("RestrictedApi")
    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}

