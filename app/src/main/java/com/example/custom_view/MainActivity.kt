package com.example.custom_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private var x = 0f
    private var y = 0f
    private var sDown: String? = null
    private var sMove: String? = null
    private var sUp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        left_toggle_button.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                right_checkable_button.isEnabled = !right_checkable_button.isEnabled
                state_text_view.text = "left_toggle_button                   " + "          " + "MotionEvent == ACTION_UP"
            }
            return@setOnTouchListener true
        }

        right_checkable_button.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        x = event?.x ?: 0f
        y = event?.y ?: 0f

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                sDown = "Down: $x,$y        MotionEvent == ACTION_DOWN"
                sMove = ""
                sUp = ""
            }
            MotionEvent.ACTION_MOVE -> {
                sDown = "                           " + "          " + "MotionEvent == ACTION_MOVE"
                sMove = "Move: $x,$y"
            }
            MotionEvent.ACTION_UP -> {
                sDown = "                           " + "          " + "MotionEvent == ACTION_UP"
                sMove = ""
                sUp = "Up: $x,$y"
                when (smile_view.state) {
                    RoundFaceView.ON -> smile_view.state = RoundFaceView.OFF
                    RoundFaceView.OFF -> smile_view.state = RoundFaceView.ON
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                sDown =
                    "                           " + "          " + "MotionEvent == ACTION_CANCEL"
            }
        }
        state_text_view.text = sDown.toString() + "\n" + sMove + "\n" + sUp
        return true
    }
}
