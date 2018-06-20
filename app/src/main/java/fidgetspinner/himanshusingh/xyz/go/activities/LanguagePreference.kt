package xyz.himanshusingh.fidgetspinner.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.himanshusingh.fidgetspinner.R


class LanguagePreference : AppCompatActivity() {
    companion object {
        fun sendToLanguagePreferenceActivity(context: Context): Intent {
            return Intent(context, LanguagePreference::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_preference)
    }
}
