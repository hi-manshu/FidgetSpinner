package fidgetspinner.himanshusingh.xyz.go.activities

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import xyz.himanshusingh.fidgetspinner.R

class RegisterActivity : AppCompatActivity() {
     companion object {
         fun sendToRegisterActivity(context:Context):Intent{
             return Intent(context,RegisterActivity::class.java)
         }
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTypeWriter()
    }
    
    private fun initTypeWriter() {
       val  animationCompleteCallBack = Handler(Handler.Callback { false })
        typewriter.setCharacterDelay(100)
        typewriter.textSize = 30F
        typewriter.setPadding(20, 20, 20, 20)
        typewriter.setAnimationCompleteListener(animationCompleteCallBack)
        typewriter.animateText("Hey there,\n Welcome to Go")
    }
}
