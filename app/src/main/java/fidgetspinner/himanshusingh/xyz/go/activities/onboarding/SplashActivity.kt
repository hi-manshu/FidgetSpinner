package xyz.himanshusingh.fidgetspinner.activities.onboarding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import xyz.himanshusingh.fidgetspinner.R
import xyz.himanshusingh.fidgetspinner.activities.LanguagePreference


class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sendToLoginOrDashboard()
    }
    
    private fun sendToLoginOrDashboard() {
        Handler().postDelayed({
            startActivity(LanguagePreference.sendToLanguagePreferenceActivity(this))
            finish()
        }, 2000)
    }
}

