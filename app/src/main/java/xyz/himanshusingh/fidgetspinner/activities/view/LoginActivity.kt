package xyz.himanshusingh.fidgetspinner.activities.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import xyz.himanshusingh.fidgetspinner.R

class LoginActivity : AppCompatActivity() {
    companion object {
        fun sendToLoginActivity(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun GoogleLoginButton(view: View){
       startActivity(GoogleFitConnect.sendToGoogleFitConnect(this))
    }
}
