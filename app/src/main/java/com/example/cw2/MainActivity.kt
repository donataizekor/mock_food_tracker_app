package com.example.cw2


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


/**
 * activity that launches the app
 */

class MainActivity : LoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        //switched to homepage if user is already logged in
        if (auth.currentUser != null) {
            homepage()
        }

        //switches to log in pages after tot seconds
        val mRunnable = Runnable {
            login()
        }

        val mHandler = Handler()
        mHandler.postDelayed(mRunnable, 2500)

        //animated colors when app is launched
        val animated = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_anim)
        animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                animation.post { animated.start() }
            }
        })
        animation.setImageDrawable(animated)
        animated?.start()
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}