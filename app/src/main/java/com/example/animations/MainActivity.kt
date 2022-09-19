package com.example.animations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
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
        private const val TRANSLATION_X_DP = 100f
        private const val TAG = "Animations"
    }

    private fun convertDpToPx(valueDp: Float): Int {
        val scale = resources.displayMetrics.density
        Log.d(TAG, "logical density of the screen : scale = $scale")
        return (valueDp * scale + 0.5).toInt()
    }

    private fun convertDpToRawPx(valueDp: Float): Float {
        val scale = resources.displayMetrics.density
        Log.d(TAG, "logical density of the screen : scale = $scale")
        return (valueDp * scale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Equivalent code but not best practices because we should use resources on Android
        //Keep this solution for the use case when we can't keep the Dp value as a resource

        // Use this if the View method needs dimension as Int
        val translationDpToPx = convertDpToPx(TRANSLATION_X_DP)
        Log.d(TAG, "translationX translationDpToPx = $translationDpToPx")
        // Use this if the View method needs dimension as Float
        val translationDpToRawPx = convertDpToRawPx(TRANSLATION_X_DP)
        Log.d(TAG, "translationX translationDpToRawPx = $translationDpToRawPx")
        droidImageView.translationX = translationDpToRawPx


        // Best practices

        // Use this if the View method needs dimension as Int
        val droidTranslationX = resources.getDimensionPixelSize(R.dimen.droid_translationX)
        Log.d(TAG, "translationX getDimensionPixelSize = $droidTranslationX")
        // Use this if the View method needs dimension as Float
        val droidTranslationRawX = resources.getDimension(R.dimen.droid_translationX)
        Log.d(TAG, "translationX getDimension = $droidTranslationRawX")
        droidImageView.translationX = droidTranslationRawX

        myButton.setOnClickListener {
            toggle = !toggle
            val myButtonTranslationRawY = resources.getDimension(R.dimen.my_button_translationY)
            val myButtonNoTranslationY = resources.getDimension(R.dimen.dimension_zero_dp)
            if (toggle) translateY(myButton, myButtonTranslationRawY) else translateY(
                myButton,
                myButtonNoTranslationY
            )
            val myTextView = TextView(this).apply {
                text = resources.getText(R.string.my_text_view_txt)
                setBackgroundColor(Color.GREEN)
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.my_text_size)
                )
                val height = resources.getDimensionPixelSize(R.dimen.dynamic_text_view_height)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    height
                ).apply {
//              layoutParams = LinearLayout.LayoutParams(
//                  LinearLayout.LayoutParams.MATCH_PARENT,
//                  LinearLayout.LayoutParams.WRAP_CONTENT
//              ).apply {
                    setMargins(0, 0, 0, 10)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
            linearLayout.addView(myTextView, 0)
        }

    }

        override fun onResume() {
            super.onResume()

            val droidTranslationY = resources.getDimension(R.dimen.droid_translationY)
            val animTrans =
                ObjectAnimator.ofFloat(droidImageView, "translationY", droidTranslationY).apply {
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

        private fun translateY(view: View, transYPx: Float) {
            ObjectAnimator.ofFloat(view, "translationY", transYPx).apply {
                duration = 2000
                start()
            }
        }
    }
