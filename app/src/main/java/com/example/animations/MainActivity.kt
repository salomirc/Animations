package com.example.animations

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var scale: Float = 0f
    private var toggle: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scale = resources.displayMetrics.density

        ObjectAnimator.ofFloat(droidImageView, "translationY", 100f * scale ).apply {
            duration = 2000
            start()
        }

        myButton.setOnClickListener {
            toggle = !toggle
            if (toggle) translateY(myButton, 100f) else translateY(myButton, 0f)
        }

    }

    private fun translateY(view: View, transY: Float) {
        ObjectAnimator.ofFloat(view, "translationY", transY * scale ).apply {
            duration = 2000
            start()
        }
    }
}
