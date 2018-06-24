package xyz.himanshusingh.fidgetspinner.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_language_preference.*
import xyz.himanshusingh.fidgetspinner.R
import xyz.himanshusingh.fidgetspinner.activities.onboarding.LoginActivity


class LanguagePreference : AppCompatActivity() {
    
    companion object {
        fun sendToLanguagePreferenceActivity(context: Context): Intent {
            return Intent(context, LanguagePreference::class.java)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_preference)
        setupUI()
        
    }
    
    private fun setupUI() {
        english_radio.setOnClickListener {}
        hindi_radio.setOnClickListener {}
        lets_go_button.setOnClickListener {startActivity(LoginActivity.sendToLoginActivity(this@LanguagePreference))}
    
    }
}
