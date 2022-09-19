package com.example.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var toggle: Boolean = false

    companion object {
        // The translation distance on the X axis expressed in dp
        private const val TRANSLATION_X_DP = -100f
        private const val TAG = "Animations"
    }

    private fun convertDpToPx(valueDp: Float): Int {
        val scale = resources.displayMetrics.density
        Log.d(TAG, "logical density of the screen : scale = $scale")
        return (valueDp * scale + 0.5).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Equivalent code but not best practices because we should use resources on Android
        //Keep this solution for the use case when we can't keep the Dp value as a resource
        val translationDpToPx = convertDpToPx(TRANSLATION_X_DP)
        Log.d(TAG, "translationX translationDpToPx = $translationDpToPx")
        droidImageView.translationX = translationDpToPx.toFloat()

        // Best practices
        val droidTranslationX = resources.getDimensionPixelSize(R.dimen.droid_translationX)
        Log.d(TAG, "translationX getDimensionPixelSize = $droidTranslationX")
        droidImageView.translationX = droidTranslationX.toFloat()

        myButton.setOnClickListener {
            toggle = !toggle
            if (toggle) translateY(myButton, -100f) else translateY(myButton, 0f)
            linearLayout.addView(
                TextView(this).apply {
                    text = "AAA"
                    setBackgroundColor(Color.GREEN)
                    textSize = 30f
                    val height = resources.getDimensionPixelSize(R.dimen.dynamic_text_view_height)

                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height).apply {
//                  layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(0, 0, 0, 10)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }, 0)
        }

    }

    override fun onResume() {
        super.onResume()

        val droidTranslationY = resources.getDimensionPixelSize(R.dimen.droid_translationY)
        val animTrans = ObjectAnimator.ofFloat(droidImageView, "translationY", droidTranslationY.toFloat() ).apply {
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
    }

    private fun translateY(view: View, transY: Float) {
        val transYPx = convertDpToPx(transY)
        Log.d(TAG, "transYPx from convertDpToPx = $transYPx")
        ObjectAnimator.ofFloat(view, "translationY", transYPx.toFloat()).apply {
            duration = 2000
            start()
        }
    }
}
