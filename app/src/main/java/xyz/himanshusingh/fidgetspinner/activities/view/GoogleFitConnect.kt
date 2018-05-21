package xyz.himanshusingh.fidgetspinner.activities.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.himanshusingh.fidgetspinner.R

class GoogleFitConnect : AppCompatActivity() {
    companion object {
        fun sendToGoogleFitConnect(context: Context): Intent {
            return Intent(context, GoogleFitConnect::class.java)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_fit_connect)
    }
}
