package fidgetspinner.himanshusingh.xyz.go.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.himanshusingh.fidgetspinner.R
import android.content.Intent
import android.os.Handler
import fidgetspinner.himanshusingh.xyz.go.activities.RegisterActivity.Companion.sendToRegisterActivity


class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)
        init()
    }
    
    private fun init() {
        Handler().postDelayed({
            startActivity(sendToRegisterActivity(this@SplashActivity))
            finish()
        }, 2000)
    }
}
