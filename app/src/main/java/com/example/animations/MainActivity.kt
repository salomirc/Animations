package com.example.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var scale: Float = 0f
    private var toggle: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scale = resources.displayMetrics.density
        droidImageView.translationX = -100f

        val animTrans = ObjectAnimator.ofFloat(droidImageView, "translationY", 100f * scale ).apply {
            duration = 2000
        }

        val animScaleX = ObjectAnimator.ofFloat(droidImageView, "scaleX", 1f, 2f).apply {
            duration = 2000
        }

        val animScaleY = ObjectAnimator.ofFloat(droidImageView, "scaleY", 1f, 2f).apply {
            duration = 2000
        }

        val animFade = ObjectAnimator.ofFloat(droidImageView, "alpha", 1f, 0f).apply {
            duration = 2000
        }

        AnimatorSet().apply {
            play(animTrans).with(animScaleX).with(animScaleY).before(animFade)
            start()
        }

        myButton.setOnClickListener {
            toggle = !toggle
//            if (toggle) translateY(myButton, -100f) else translateY(myButton, 0f)
            linearLayout.addView(TextView(this).apply {
                text = "AAA"
                setBackgroundColor(Color.GREEN)
                val height = 50 * scale
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (height.toInt())).apply {
                    setMargins(0, 0, 0, 10)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }, 0)
        }

    }

    private fun translateY(view: View, transY: Float) {
        ObjectAnimator.ofFloat(view, "translationY", transY * scale ).apply {
            duration = 2000
            start()
        }
    }
}
