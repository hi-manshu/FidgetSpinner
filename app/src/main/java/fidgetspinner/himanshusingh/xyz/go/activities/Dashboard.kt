package xyz.himanshusingh.fidgetspinner.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.himanshusingh.fidgetspinner.R

class Dashboard : AppCompatActivity() {
    companion object {
        fun sendToDashboard(context: Context): Intent {
            return Intent(context, Dashboard::class.java)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}

